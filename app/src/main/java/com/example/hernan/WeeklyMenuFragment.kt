package com.example.hernan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hernan.databinding.FragmentWeeklyMenuBinding
import com.example.hernan.viewmodel.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeeklyMenuFragment : Fragment() {

    private var _binding: FragmentWeeklyMenuBinding? = null
    private val binding get() = _binding!!
    private val menuViewModel: MenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeeklyMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        menuViewModel.loadWeeklyMenus()
    }

    private fun setupRecyclerView() {
        binding.rvWeeklyMenus.layoutManager = LinearLayoutManager(context)
        binding.rvWeeklyMenus.adapter = WeeklyMenuAdapter(emptyList())
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                menuViewModel.weeklyMenus.collect { menus ->
                    (binding.rvWeeklyMenus.adapter as WeeklyMenuAdapter).updateMenus(menus)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}