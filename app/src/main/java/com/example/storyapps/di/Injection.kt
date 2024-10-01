package com.example.storyapps.di

import com.example.storyapps.data.api.ApiConfig
import com.example.storyapps.repository.UserRegisterRepository

object Injection {

    fun provideUserRepository(): UserRegisterRepository {
        val apiService = ApiConfig.getApiService()
        return UserRegisterRepository(apiService)
    }
}