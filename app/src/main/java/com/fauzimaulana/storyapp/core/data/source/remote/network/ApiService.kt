package com.fauzimaulana.storyapp.core.data.source.remote.network

import com.fauzimaulana.storyapp.core.data.source.remote.response.AddNewStoryResponse
import com.fauzimaulana.storyapp.core.data.source.remote.response.ListStoryResponse
import com.fauzimaulana.storyapp.core.data.source.remote.response.LoginResponse
import com.fauzimaulana.storyapp.core.data.source.remote.response.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SignUpResponse

    @FormUrlEncoded
    @POST("/login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @Multipart
    @POST("/stories")
    fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Header("Authorization") token: String
    ): AddNewStoryResponse

    @GET("/stories")
    fun getAllStories(
        @Header("Authorization") token: String
    ): ListStoryResponse
}