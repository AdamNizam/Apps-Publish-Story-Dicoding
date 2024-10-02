package com.example.storyapps.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.storyapps.data.api.retrofit.ApiConfig
import com.example.storyapps.repository.RegisterRepository
import com.example.storyapps.repository.StoryRepository
import com.example.storyapps.repository.UserLoginRepository
import com.example.storyapps.ui.login.LoginViewModelFactory

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

object Injection {

    fun provideUserRepository(): RegisterRepository {
        val apiService = ApiConfig.getApiService()
        return RegisterRepository(apiService)
    }

    fun provideUserLoginRepository(context: Context): UserLoginRepository {
        val apiService = ApiConfig.getApiService()
        val dataStore = context.dataStore
        return UserLoginRepository(apiService, dataStore)
    }
    fun provideStoryRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        val dataStore = context.dataStore
        return StoryRepository(apiService, dataStore)
    }

    fun provideLoginViewModelFactory(context: Context): LoginViewModelFactory {
        val userLoginRepository = provideUserLoginRepository(context)
        return LoginViewModelFactory(userLoginRepository)
    }

}