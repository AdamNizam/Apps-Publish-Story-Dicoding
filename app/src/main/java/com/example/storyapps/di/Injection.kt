package com.example.storyapps.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.storyapps.data.api.retrofit.ApiConfig
import com.example.storyapps.repository.RegisterRepository
import com.example.storyapps.repository.StoryRepository
import com.example.storyapps.repository.UserLoginRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

object Injection {

    private val apiService = ApiConfig.getApiService()

    fun provideUserRepository(): RegisterRepository {
        return RegisterRepository(apiService)
    }

    fun provideUserLoginRepository(context: Context): UserLoginRepository {
        return UserLoginRepository(apiService, context.dataStore)
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        return StoryRepository(apiService, context.dataStore)
    }

}