package com.example.hernan.data.repository

import com.example.hernan.data.model.Menu
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepository @Inject constructor(
    private val db: FirebaseFirestore
) {
    suspend fun getMenuByDate(date: Date): Menu? {
        return try {
            val dateStart = Calendar.getInstance().apply {
                time = date
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time

            val dateEnd = Calendar.getInstance().apply {
                time = date
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }.time

            val querySnapshot = db.collection("menus")
                .whereGreaterThanOrEqualTo("date", dateStart)
                .whereLessThanOrEqualTo("date", dateEnd)
                .limit(1)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                querySnapshot.documents[0].toObject<Menu>()?.apply {
                    id = querySnapshot.documents[0].id
                }
            } else {
                null
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getWeeklyMenus(): List<Menu> {
        return try {
            val calendar = Calendar.getInstance()
            val startDate = calendar.apply {
                set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time

            val endDate = calendar.apply {
                add(Calendar.DAY_OF_WEEK, 6)
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }.time

            val querySnapshot = db.collection("menus")
                .whereGreaterThanOrEqualTo("date", startDate)
                .whereLessThanOrEqualTo("date", endDate)
                .orderBy("date")
                .get()
                .await()

            querySnapshot.documents.mapNotNull { doc ->
                doc.toObject<Menu>()?.apply { id = doc.id }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}