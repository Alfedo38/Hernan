package com.example.hernan.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return Firebase.firestore.apply {
            firestoreSettings = firestoreSettings {
                isPersistenceEnabled = true
            }
        }
    }

    @Provides
    @Singleton
    fun provideMenuRepository(db: FirebaseFirestore): MenuRepository {
        return MenuRepository(db)
    }
}