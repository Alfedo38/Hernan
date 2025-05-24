package com.example.hernan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hernan.data.model.Menu
import com.example.hernan.data.repository.MenuRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

sealed class MenuUiState {
    object Loading : MenuUiState()
    data class Success(val menu: Menu?) : MenuUiState()
    data class Error(val message: String) : MenuUiState()
}

class MenuViewModel(private val menuRepository: MenuRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<MenuUiState>(MenuUiState.Loading)
    val uiState: StateFlow<MenuUiState> = _uiState

    private val _weeklyMenus = MutableStateFlow<List<Menu>>(emptyList())
    val weeklyMenus: StateFlow<List<Menu>> = _weeklyMenus

    fun loadTodayMenu() {
        viewModelScope.launch {
            _uiState.value = MenuUiState.Loading
            try {
                val today = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.time

                val menu = menuRepository.getMenuByDate(today)
                _uiState.value = MenuUiState.Success(menu)
            } catch (e: Exception) {
                _uiState.value = MenuUiState.Error("Error al cargar el menú: ${e.message}")
            }
        }
    }

    fun loadWeeklyMenus() {
        viewModelScope.launch {
            try {
                val menus = menuRepository.getWeeklyMenus()
                _weeklyMenus.value = menus
            } catch (e: Exception) {
                _uiState.value = MenuUiState.Error("Error al cargar los menús semanales: ${e.message}")
            }
        }
    }
}