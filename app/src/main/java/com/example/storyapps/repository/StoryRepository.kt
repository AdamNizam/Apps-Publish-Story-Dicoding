package com.example.storyapps.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.storyapps.data.api.response.StoryResponse
import com.example.storyapps.data.api.retrofit.ApiService
import kotlinx.coroutines.flow.first

class StoryRepository(
    private val apiService: ApiService,
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val KEY_TOKEN = stringPreferencesKey("auth_token")
    }

    private suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[KEY_TOKEN]
    }

    suspend fun getStories(page: Int? = null, size: Int? = null, location: Int? = null): StoryResponse {
        val token = getToken()
        return if (token != null) {
            apiService.getStories(token = "Bearer $token", page = page, size = size, location = location)
        } else {
            throw Exception("Token not found")
        }
    }
}
