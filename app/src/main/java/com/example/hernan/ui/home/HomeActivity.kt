package com.example.hernan.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.hernan.databinding.ActivityHomeBinding
import com.example.hernan.viewmodel.MenuViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val menuViewModel: MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupNavigationDrawer()
        setupObservers()
        loadData()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun setupNavigationDrawer() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_daily_menu -> {
                    // Cargar DailyMenuFragment (ya manejado por loadData)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_weekly_menu -> {
                    // TODO: Implementar WeeklyMenuFragment
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }R.id.nav_weekly_menu -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, WeeklyMenuFragment())
                    .commit()
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
                else -> false
            }
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                menuViewModel.loadTodayMenu()
            }
        }
    }

    private fun setupObservers() {
        menuViewModel.todayMenu.observe(this) { menu ->
            menu?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, DailyMenuFragment.newInstance(it))
                    .commit()
            } ?: run {
                binding.tvEmptyMessage.visibility = View.VISIBLE
            }
        }

        menuViewModel.errorMessage.observe(this) { error ->
            error?.let {
                showErrorSnackbar(it)
                menuViewModel.errorMessage.value = null
            }
        }
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}