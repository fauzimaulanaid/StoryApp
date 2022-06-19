package com.fauzimaulana.storyapp.core.domain.usecase

import com.fauzimaulana.storyapp.core.domain.model.*
import com.fauzimaulana.storyapp.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface StoryUseCase {
    fun registerUser(name: String, email: String, password: String): Flow<Resource<SignUpModel>>

    fun userLogin(email: String, password: String): Flow<Resource<LoginModel>>

    fun uploadStory(file: MultipartBody.Part, description: RequestBody, token: String): Flow<Resource<AddNewStoryModel>>

    fun getAllStories(token: String): Flow<Resource<List<StoryModel>>>

    fun getUserPref(): Flow<UserModel>

    suspend fun saveUser(user: UserModel)

    suspend fun logout()
}