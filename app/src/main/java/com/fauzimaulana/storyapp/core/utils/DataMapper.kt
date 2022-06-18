package com.fauzimaulana.storyapp.core.utils

import com.fauzimaulana.storyapp.core.data.source.remote.response.*
import com.fauzimaulana.storyapp.core.domain.model.*

object DataMapper {
    fun mapSIgnUpResponseToDomain(input: SignUpResponse) = SignUpModel(
        error = input.error,
        message = input.message
    )

    fun mapLoginResponseToDomain(input: LoginResponse) = LoginModel(
        error = input.error,
        message = input.message,
        loginResult = LoginResultModel(
            input.loginResult.name,
            input.loginResult.userId,
            input.loginResult.token
        )
    )

    fun mapAddNewStoryResponseToDomain(input: AddNewStoryResponse) = AddNewStoryModel(
        error = input.error,
        message = input.message
    )

    fun mapStoryResponseToDomain(input: List<StoryResponse>): List<StoryModel> =
        input.map {
            StoryModel(
                id = it.id,
                name = it.name,
                description = it.description,
                photoUrl = it.photoUrl,
                createAt = it.createdAt,
                lat = it.lat,
                lon = it.lon
            )
        }
}