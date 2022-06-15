package com.fauzimaulana.storyapp.domain.repository

import com.fauzimaulana.storyapp.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface IUserPreference {
    fun getUser(): Flow<UserModel>

    suspend fun saveUser(user: UserModel)

    suspend fun login()

    suspend fun logout()
}