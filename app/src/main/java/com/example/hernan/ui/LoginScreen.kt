package com.example.hernan.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hernan.viewmodel.AuthViewModel

@Composable
fun LoginScreen(viewModel: AuthViewModel = hiltViewModel()) {
    val state = viewModel.authState.collectAsState()
    Text(text = "Pantalla de Login (En construcción)")
    // TODO: Añadir UI de login con campos de email y contraseña
}