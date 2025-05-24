package com.example.hernan.data.repository

class AuthRepository {
    private val firebaseAuth = Firebase.auth

    suspend fun loginWithEmail(email: String, password: String): FirebaseUser {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user ?: throw Exception("Usuario no encontrado")
        } catch (e: Exception) {
            throw when (e) {
                is FirebaseAuthInvalidUserException -> Exception("Usuario no registrado")
                is FirebaseAuthInvalidCredentialsException -> Exception("Credenciales incorrectas")
                else -> Exception("Error al iniciar sesión: ${e.message}")
            }
        }
    }
}