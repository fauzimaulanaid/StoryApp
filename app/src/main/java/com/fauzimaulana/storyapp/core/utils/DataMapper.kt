package com.fauzimaulana.storyapp.core.utils

import com.fauzimaulana.storyapp.core.data.source.remote.response.AddNewStoryResponse
import com.fauzimaulana.storyapp.core.data.source.remote.response.LoginResult
import com.fauzimaulana.storyapp.core.data.source.remote.response.SignUpResponse
import com.fauzimaulana.storyapp.core.data.source.remote.response.StoryResponse
import com.fauzimaulana.storyapp.core.domain.model.AddNewStoryModel
import com.fauzimaulana.storyapp.core.domain.model.LoginModel
import com.fauzimaulana.storyapp.core.domain.model.SignUpModel
import com.fauzimaulana.storyapp.core.domain.model.StoryModel

object DataMapper {
    fun mapSIgnUpResponseToDomain(input: SignUpResponse) = SignUpModel(
        error = input.error,
        message = input.message
    )

    fun mapLoginResponseToDomain(input: LoginResult) = LoginModel(
        name = input.name,
        userId = input.userId,
        token = input.token
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