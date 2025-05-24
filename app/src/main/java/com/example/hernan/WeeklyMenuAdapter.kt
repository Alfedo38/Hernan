package com.example.hernan.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hernan.R
import com.example.hernan.data.model.Menu

class WeeklyMenuAdapter : RecyclerView.Adapter<WeeklyMenuAdapter.ViewHolder>() {
    var menus: List<Menu> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = menus[position]
        holder.date.text = menu.date
        holder.mainDish.text = menu.mainDish
        holder.sideDish.text = menu.sideDish
        holder.dessert.text = menu.dessert
        holder.calories.text = menu.calories.toString()
    }

    override fun getItemCount(): Int = menus.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.dateTextView)
        val mainDish: TextView = view.findViewById(R.id.mainDishTextView)
        val sideDish: TextView = view.findViewById(R.id.sideDishTextView)
        val dessert: TextView = view.findViewById(R.id.dessertTextView)
        val calories: TextView = view.findViewById(R.id.caloriesTextView)
    }
}