package com.fauzimaulana.storyapp.core.data.source.remote

import android.util.Log
import com.fauzimaulana.storyapp.core.data.source.remote.network.ApiService
import com.fauzimaulana.storyapp.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.Exception

class RemoteDataSource(private val apiService: ApiService) {

    fun registerUser(name: String, email: String, password: String): Flow<ApiResponse<SignUpResponse>> {
        return flow {
            try {
                val response = apiService.registerUser(name, email, password)
                if (response.error) {
                    emit(ApiResponse.Error(response.message))
                    Log.e("RemoteDataSource: ", response.message)
                } else {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource: ", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun userLogin(email: String, password: String): Flow<ApiResponse<LoginResponse>> {
        return flow {
            try {
                val response = apiService.userLogin(email, password)
                if (response.error) {
                    emit(ApiResponse.Error(response.message))
                    Log.e("RemoteDataSource: ", response.message)
                } else {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource: ", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun uploadStory(file: MultipartBody.Part, description: RequestBody, token: String): Flow<ApiResponse<AddNewStoryResponse>> {
        return flow {
            try {
                val response = apiService.uploadStory(file, description, "Bearer $token")
                if (response.error) {
                    emit(ApiResponse.Error(response.message))
                    Log.e("RemoteDataSource: ", response.message)
                } else {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource: ", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getAllStories(token: String): Flow<ApiResponse<List<StoryResponse>>> {
        return flow {
            try {
                val response = apiService.getAllStories("Bearer $token")
                val dataArray = response.listStory
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.listStory))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource: ", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}