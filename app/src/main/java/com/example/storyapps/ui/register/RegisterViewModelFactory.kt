package com.example.storyapps.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapps.repository.UserRegisterRepository

class RegisterViewModelFactory (private val userRegisterRepository: UserRegisterRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            RegisterViewModel(userRegisterRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}