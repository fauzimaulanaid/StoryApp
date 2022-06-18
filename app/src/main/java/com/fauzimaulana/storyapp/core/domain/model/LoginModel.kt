package com.fauzimaulana.storyapp.core.domain.model

data class LoginModel(
    val error: Boolean,
    val message: String,
    val loginResult: LoginResultModel
)

data class LoginResultModel(
    val name: String,
    val userId: String,
    val token: String
)
