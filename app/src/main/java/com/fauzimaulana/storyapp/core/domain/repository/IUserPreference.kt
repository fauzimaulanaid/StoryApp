package com.fauzimaulana.storyapp.core.domain.repository

import com.fauzimaulana.storyapp.core.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface IUserPreference {
    fun getUser(): Flow<UserModel>

    suspend fun saveUser(user: UserModel)

    suspend fun login()

    suspend fun logout()
}