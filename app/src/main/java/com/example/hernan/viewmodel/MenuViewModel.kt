package com.example.hernan.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hernan.data.repository.MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val repository: MenuRepository
) : ViewModel() {
    // TODO: Implementar lógica para manejar menús
}