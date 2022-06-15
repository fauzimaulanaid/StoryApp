package com.fauzimaulana.storyapp.domain.usecase

import com.fauzimaulana.storyapp.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface StoryUseCase {
    fun getUser(): Flow<UserModel>

    suspend fun saveUser(user: UserModel)

    suspend fun login()

    suspend fun logout()
}