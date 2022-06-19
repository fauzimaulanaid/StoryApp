package com.fauzimaulana.storyapp.core.data

import com.fauzimaulana.storyapp.core.data.source.remote.ApiResponse
import com.fauzimaulana.storyapp.core.data.source.remote.RemoteDataSource
import com.fauzimaulana.storyapp.core.data.source.remote.response.*
import com.fauzimaulana.storyapp.core.domain.model.*
import com.fauzimaulana.storyapp.core.domain.repository.IStoryRepository
import com.fauzimaulana.storyapp.core.utils.DataMapper
import com.fauzimaulana.storyapp.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(
    private val userPreference: UserPreference,
    private val remoteDataSource: RemoteDataSource): IStoryRepository {
    override fun registerUser(
        name: String,
        email: String,
        password: String
    ): Flow<Resource<SignUpModel>> {
        return object : NetworkBoundResource<SignUpModel, SignUpResponse>() {
            override fun createCall(): Flow<ApiResponse<SignUpResponse>> =
                remoteDataSource.registerUser(name, email, password)

            override fun loadData(data: SignUpResponse): SignUpModel =
                DataMapper.mapSIgnUpResponseToDomain(data)
        }.asFlow()
    }

    override fun userLogin(email: String, password: String): Flow<Resource<LoginModel>> {
        return object : NetworkBoundResource<LoginModel, LoginResponse>() {
            override fun createCall(): Flow<ApiResponse<LoginResponse>> =
                remoteDataSource.userLogin(email, password)

            override fun loadData(data: LoginResponse): LoginModel =
                DataMapper.mapLoginResponseToDomain(data)
        }.asFlow()
    }

    override fun uploadStory(
        file: MultipartBody.Part,
        description: RequestBody,
        token: String
    ): Flow<Resource<AddNewStoryModel>> {
        return object : NetworkBoundResource<AddNewStoryModel, AddNewStoryResponse>() {
            override fun createCall(): Flow<ApiResponse<AddNewStoryResponse>> =
                remoteDataSource.uploadStory(file, description, token)

            override fun loadData(data: AddNewStoryResponse): AddNewStoryModel =
                DataMapper.mapAddNewStoryResponseToDomain(data)
        }.asFlow()
    }

    override fun getAllStories(token: String): Flow<Resource<List<StoryModel>>> {
        return object : NetworkBoundResource<List<StoryModel>, List<StoryResponse>>() {
            override fun createCall(): Flow<ApiResponse<List<StoryResponse>>> =
                remoteDataSource.getAllStories(token)

            override fun loadData(data: List<StoryResponse>): List<StoryModel> =
                DataMapper.mapStoryResponseToDomain(data)
        }.asFlow()
    }

    override fun getUserPref(): Flow<UserModel> = userPreference.getUser()

    override suspend fun saveUser(user: UserModel) = userPreference.saveUser(user)

    override suspend fun logout() = userPreference.logout()
}