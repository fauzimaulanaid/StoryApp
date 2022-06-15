package com.fauzimaulana.storyapp.domain.usecase

import com.fauzimaulana.storyapp.domain.model.UserModel
import com.fauzimaulana.storyapp.domain.repository.IUserPreference
import kotlinx.coroutines.flow.Flow

class StoryInteractor(private val userPreference: IUserPreference): StoryUseCase {
    override fun getUser(): Flow<UserModel> = userPreference.getUser()

    override suspend fun saveUser(user: UserModel) = userPreference.saveUser(user)

    override suspend fun login() = userPreference.login()

    override suspend fun logout() = userPreference.logout()

}