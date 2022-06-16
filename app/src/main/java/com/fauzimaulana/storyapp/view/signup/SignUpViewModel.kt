package com.fauzimaulana.storyapp.view.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fauzimaulana.storyapp.core.domain.model.UserModel
import com.fauzimaulana.storyapp.core.domain.usecase.StoryUseCase
import kotlinx.coroutines.launch

class SignUpViewModel(private val storyUseCase: StoryUseCase): ViewModel() {
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            storyUseCase.saveUser(user)
        }
    }
}