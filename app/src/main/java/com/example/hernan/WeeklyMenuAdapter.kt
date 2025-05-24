package com.example.hernan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hernan.databinding.ItemMenuBinding
import com.example.hernan.data.model.Menu
import java.text.SimpleDateFormat
import java.util.*

class WeeklyMenuAdapter(private var menus: List<Menu>) :
    RecyclerView.Adapter<WeeklyMenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: Menu) {
            val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
            binding.tvMenuDate.text = dateFormat.format(menu.date)
            binding.tvMainDish.text = menu.mainDish
            binding.tvSideDish.text = menu.sideDish
            binding.tvDessert.text = menu.dessert
            binding.tvCalories.text = "${menu.calories} kcal"
            binding.tvIngredients.text = menu.ingredients.joinToString(", ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menus[position])
    }

    override fun getItemCount(): Int = menus.size

    fun updateMenus(newMenus: List<Menu>) {
        menus = newMenus
        notifyDataSetChanged()
    }
}