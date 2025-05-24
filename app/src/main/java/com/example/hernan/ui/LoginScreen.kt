package com.example.hernan.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hernan.viewmodel.AuthViewModel
import com.example.hernan.viewmodel.AuthState

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(authViewModel.loginState) {
        authViewModel.loginState.collect { state ->
            when (state) {
                is AuthState.Loading -> { /* Mostrar carga */ }
                is AuthState.Success -> onNavigateToHome()
                is AuthState.Error -> { /* Mostrar error */ }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null,
            supportingText = { passwordError?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                emailError = if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    "Ingresa un email válido"
                } else null
                passwordError = if (password.isEmpty() || password.length < 6) {
                    "La contraseña debe tener al menos 6 caracteres"
                } else null
                if (emailError == null && passwordError == null) {
                    authViewModel.loginUser(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onNavigateToRegister) {
            Text("Registrarse")
        }
    }
}