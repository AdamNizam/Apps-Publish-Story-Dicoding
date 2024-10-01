package com.example.storyapps.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapps.data.api.response.RegisterResponse
import com.example.storyapps.repository.RegisterRepository
import kotlinx.coroutines.launch

class RegisterViewModel (private val userRegisterRepository: RegisterRepository): ViewModel() {
    fun register(name: String, email: String, password: String, onResult: (RegisterResponse) -> Unit) {
        viewModelScope.launch {
            try {
                val response = userRegisterRepository.register(name, email, password)
                onResult(response)
            } catch (e: Exception) {
                // Handle error
                onResult(RegisterResponse(error = true, message = e.message ?: "Unknown error"))
            }
        }
    }
}