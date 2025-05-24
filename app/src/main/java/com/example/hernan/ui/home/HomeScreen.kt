package com.example.hernan.ui.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hernan.data.model.Menu
import com.example.hernan.viewmodel.MenuViewModel

@Composable
fun HomeScreen(viewModel: MenuViewModel = hiltViewModel()) {
    val menus by viewModel.menus.collectAsState()
    LazyColumn {
        items(menus) { menu ->
            MenuItem(menu)
        }
    }
}

@Composable
fun MenuItem(menu: Menu) {
    Text(text = "${menu.date}: ${menu.mainDish}")
    // TODO: Añadir más detalles del menú
}