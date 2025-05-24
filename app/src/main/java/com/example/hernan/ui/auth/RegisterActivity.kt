package com.example.hernan

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hernan.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.util.Log

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val name = binding.etName.text.toString()

            if (validateInputs(email, password, name)) {
                registerUser(email, password, name)
            }
        }
    }

    private fun validateInputs(email: String, password: String, name: String): Boolean {
        if (name.isEmpty()) {
            binding.etName.error = "Ingresa un nombre completo"
            return false
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Ingresa un email válido"
            return false
        }

        if (password.isEmpty() || password.length < 6) {
            binding.etPassword.error = "La contraseña debe tener al menos 6 caracteres"
            return false
        }

        return true
    }

    private fun registerUser(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveUserData(name, email)
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserData(name: String, email: String) {
        val user = auth.currentUser
        user?.let {
            val db = Firebase.firestore
            val userData = hashMapOf(
                "name" to name,
                "email" to email,
                "createdAt" to FieldValue.serverTimestamp()
            )

            db.collection("users").document(user.uid)
                .set(userData)
                .addOnSuccessListener {
                    Log.d("Register", "Datos de usuario guardados")
                }
                .addOnFailureListener { e ->
                    Log.w("Register", "Error al guardar datos de usuario", e)
                }
        }
    }
}