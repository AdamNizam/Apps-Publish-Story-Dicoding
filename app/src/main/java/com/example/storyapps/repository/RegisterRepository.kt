package com.example.storyapps.repository

import com.example.storyapps.data.api.retrofit.ApiService
import com.example.storyapps.data.api.response.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterRepository (private val apiService: ApiService){
    suspend fun register(name: String, email: String, password: String): BaseResponse {
        return withContext(Dispatchers.IO) {
            apiService.register(name, email, password)
        }
    }
}