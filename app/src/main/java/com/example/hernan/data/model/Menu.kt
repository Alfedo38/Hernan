package com.example.hernan.data.model

import com.google.firebase.firestore.PropertyName

data class Menu(
    @PropertyName("id") val id: String = "",
    @PropertyName("name") val name: String = "",
    @PropertyName("description") val description: String = ""
)