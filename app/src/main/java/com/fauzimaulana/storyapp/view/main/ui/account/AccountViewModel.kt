package com.fauzimaulana.storyapp.view.main.ui.account

import androidx.lifecycle.*
import com.fauzimaulana.storyapp.core.domain.model.UserModel
import com.fauzimaulana.storyapp.core.domain.usecase.StoryUseCase
import kotlinx.coroutines.launch

class AccountViewModel(private val storyUseCase: StoryUseCase) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return storyUseCase.getUserPref().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            storyUseCase.logout()
        }
    }
}