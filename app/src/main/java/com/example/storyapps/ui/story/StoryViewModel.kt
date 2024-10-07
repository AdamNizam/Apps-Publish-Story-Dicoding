package com.example.storyapps.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.storyapps.data.api.response.ListStoryItem
import com.example.storyapps.data.api.response.StoryResponse
import com.example.storyapps.repository.StoryRepository
import kotlinx.coroutines.launch

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    private val _stories = MutableLiveData<List<ListStoryItem?>?>()
    val stories: LiveData<List<ListStoryItem>> get() = _stories.map { it?.filterNotNull() ?: emptyList() } // Menghapus elemen null

    fun fetchStories(page: Int? = null, size: Int? = null, location: Int? = null) {
        viewModelScope.launch {
            try {
                val response: StoryResponse = storyRepository.getStories(page, size, location)

                _stories.value = response.listStory
            } catch (e: Exception) {
                _stories.value = null
            }
        }
    }
}
