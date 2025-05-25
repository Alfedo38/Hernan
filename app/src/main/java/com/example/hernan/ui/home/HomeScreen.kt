package com.example.hernan.ui.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hernan.data.model.Menu
import com.example.hernan.viewmodel.MenuViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MenuViewModel = hiltViewModel()
) {
    val menus by viewModel.menus.collectAsState() // Usar 'by' para collectAsState
    LazyColumn {
        items(menus) { menu -> // Iterar directamente sobre menus
            MenuItem(
                menu = menu,
                onEdit = { navController.navigate("edit_menu/${menu.id}") }
            )
        }
    }
}

@Composable
fun MenuItem(menu: Menu, onEdit: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Fecha: ${menu.date}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Plato principal: ${menu.mainDish}")
            Text(text = "Acompañamiento: ${menu.sideDish}")
            Text(text = "Postre: ${menu.dessert}")
            Text(text = "Calorías: ${menu.calories}")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onEdit) {
                Text("Editar")
            }
        }
    }
}