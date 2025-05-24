package com.example.hernan.data.repository

import com.example.hernan.data.model.Menu
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MenuRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getMenus(): List<Menu> {
        return try {
            db.collection("menus")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Menu::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}