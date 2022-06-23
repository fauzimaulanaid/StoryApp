package com.fauzimaulana.storyapp.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fauzimaulana.storyapp.core.domain.model.SignUpModel
import com.fauzimaulana.storyapp.core.domain.usecase.StoryUseCase
import com.fauzimaulana.storyapp.core.vo.Resource

class SignUpViewModel(private val storyUseCase: StoryUseCase): ViewModel() {
    fun registerUser(name: String, email: String, password: String): LiveData<Resource<SignUpModel>> =
        storyUseCase.registerUser(name, email, password).asLiveData()
}