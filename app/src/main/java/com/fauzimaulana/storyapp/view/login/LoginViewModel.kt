package com.fauzimaulana.storyapp.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fauzimaulana.storyapp.core.domain.model.LoginModel
import com.fauzimaulana.storyapp.core.domain.model.UserModel
import com.fauzimaulana.storyapp.core.domain.usecase.StoryUseCase
import com.fauzimaulana.storyapp.core.vo.Resource
import kotlinx.coroutines.launch

class LoginViewModel(private val storyUseCase: StoryUseCase): ViewModel() {

    fun userLogin(email: String, password: String): LiveData<Resource<LoginModel>> =
        storyUseCase.userLogin(email, password).asLiveData()

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            storyUseCase.saveUser(user)
        }
    }

    fun getUser(): LiveData<UserModel> {
        return storyUseCase.getUserPref().asLiveData()
    }

    fun login() {
        viewModelScope.launch {
            storyUseCase.loginUser()
        }
    }
}