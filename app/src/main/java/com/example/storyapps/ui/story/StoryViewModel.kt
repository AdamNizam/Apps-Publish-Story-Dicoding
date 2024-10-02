package com.example.storyapps.ui.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.storyapps.repository.StoryRepository

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun getStories(page: Int? = null, size: Int? = null, location: Int? = null) = liveData {
        try {
            val response = storyRepository.getStories(page, size, location)
            emit(response)
        } catch (e: Exception) {
            emit(null)
        }
    }
}
