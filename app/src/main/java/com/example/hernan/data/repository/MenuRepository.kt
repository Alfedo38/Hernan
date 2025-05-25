package com.example.hernan.data.repository

import com.example.hernan.data.model.Menu
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getMenus(): List<Menu> {
        return try {
            val snapshot = firestore.collection("menus").get().await()
            snapshot.documents.mapNotNull { it.toObject(Menu::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}