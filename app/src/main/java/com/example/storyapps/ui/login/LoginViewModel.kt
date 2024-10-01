package com.example.storyapps.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.storyapps.repository.UserLoginRepository
import kotlinx.coroutines.flow.Flow

class LoginViewModel (private val userLoginRepository: UserLoginRepository): ViewModel() {

    fun login(email: String, password: String) = liveData {
        try {
            val response = userLoginRepository.login(email, password)
            if (!response.error) {
                userLoginRepository.saveToken(response.loginResult.token)
                emit(Result.success(response))
            } else {
                emit(Result.failure(Exception(response.message)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getToken(): Flow<String?> {
        return userLoginRepository.getToken()
    }
}