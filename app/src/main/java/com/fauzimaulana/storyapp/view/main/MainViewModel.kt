package com.fauzimaulana.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fauzimaulana.storyapp.core.domain.model.UserModel
import com.fauzimaulana.storyapp.core.domain.usecase.StoryUseCase

class MainViewModel(private val storyUseCase: StoryUseCase): ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return storyUseCase.getUser().asLiveData()
    }
}