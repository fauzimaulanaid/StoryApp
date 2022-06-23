package com.fauzimaulana.storyapp.core.data.source.remote.network

import com.fauzimaulana.storyapp.core.data.source.remote.response.AddNewStoryResponse
import com.fauzimaulana.storyapp.core.data.source.remote.response.ListStoryResponse
import com.fauzimaulana.storyapp.core.data.source.remote.response.LoginResponse
import com.fauzimaulana.storyapp.core.data.source.remote.response.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SignUpResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Header("Authorization") token: String
    ): AddNewStoryResponse

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String
    ): ListStoryResponse
}