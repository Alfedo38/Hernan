package com.example.hernan.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hernan.databinding.ActivityAdminBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar AdminFragment o configurar navegación
    }
}