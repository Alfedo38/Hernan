package com.example.hernan.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hernan.data.model.Menu
import com.example.hernan.viewmodel.MenuViewModel

@Composable
fun EditMenuScreen(
    menuId: String,
    viewModel: MenuViewModel = hiltViewModel(),
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    var mainDish by remember { mutableStateOf("") }
    var sideDish by remember { mutableStateOf("") }
    var dessert by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    LaunchedEffect(menuId) {
        viewModel.getMenuById(menuId)?.let { menu ->
            mainDish = menu.mainDish
            sideDish = menu.sideDish
            dessert = menu.dessert
            calories = menu.calories.toString()
            date = menu.date
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Editar Menú", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Fecha (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = mainDish,
            onValueChange = { mainDish = it },
            label = { Text("Plato Principal") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = sideDish,
            onValueChange = { sideDish = it },
            label = { Text("Acompañamiento") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = dessert,
            onValueChange = { dessert = it },
            label = { Text("Postre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = calories,
            onValueChange = { calories = it },
            label = { Text("Calorías") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    val updatedMenu = Menu(
                        id = menuId,
                        date = date,
                        mainDish = mainDish,
                        sideDish = sideDish,
                        dessert = dessert,
                        calories = calories.toIntOrNull() ?: 0,
                        ingredients = emptyList()
                    )
                    viewModel.updateMenu(updatedMenu)
                    onSave()
                },
                enabled = mainDish.isNotBlank() && sideDish.isNotBlank() && date.isNotBlank()
            ) {
                Text("Guardar")
            }
            Button(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    }
}