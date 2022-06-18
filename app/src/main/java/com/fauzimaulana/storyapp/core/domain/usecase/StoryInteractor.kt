package com.fauzimaulana.storyapp.core.domain.usecase

import com.fauzimaulana.storyapp.core.domain.model.*
import com.fauzimaulana.storyapp.core.domain.repository.IStoryRepository
import com.fauzimaulana.storyapp.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryInteractor(private val storyRepository: IStoryRepository): StoryUseCase {
    override fun registerUser(
        name: String,
        email: String,
        password: String
    ): Flow<Resource<SignUpModel>> =
        storyRepository.registerUser(name, email, password)

    override fun userLogin(email: String, password: String): Flow<Resource<LoginModel>> =
        storyRepository.userLogin(email, password)

    override fun uploadStory(
        file: MultipartBody.Part,
        description: RequestBody,
        token: String
    ): Flow<Resource<AddNewStoryModel>> =
        storyRepository.uploadStory(file, description, token)

    override fun getAllStories(token: String): Flow<Resource<List<StoryModel>>> =
        storyRepository.getAllStories(token)

    override fun getUserPref(): Flow<UserModel> =
        storyRepository.getUserPref()

    override suspend fun saveUser(user: UserModel) =
        storyRepository.saveUser(user)

    override suspend fun loginUser() =
        storyRepository.loginUser()

    override suspend fun logout() =
        storyRepository.logout()
}