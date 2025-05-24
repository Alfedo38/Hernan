package com.example.hernan.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val id: String = "",
    val date: String = "",
    val mainDish: String = "",
    val sideDish: String = "",
    val dessert: String = "",
    val calories: Int = 0,
    val ingredients: List<String> = emptyList()
) : Parcelable