package com.example.hernan

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.hernan.ui.LoginScreen
import com.example.hernan.ui.theme.HernanTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HernanTheme {
                LoginScreen(
                    onNavigateToHome = {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    },
                    onNavigateToRegister = {
                        startActivity(Intent(this, RegisterActivity::class.java))
                    }
                )
            }
        }
    }
}