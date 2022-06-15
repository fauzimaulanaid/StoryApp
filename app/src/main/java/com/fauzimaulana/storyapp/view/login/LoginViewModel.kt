package com.fauzimaulana.storyapp.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fauzimaulana.storyapp.domain.model.UserModel
import com.fauzimaulana.storyapp.domain.usecase.StoryUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val storyUseCase: StoryUseCase): ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return storyUseCase.getUser().asLiveData()
    }

    fun login() {
        viewModelScope.launch {
            storyUseCase.login()
        }
    }
}