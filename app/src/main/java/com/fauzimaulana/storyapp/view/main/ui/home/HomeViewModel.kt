package com.fauzimaulana.storyapp.view.main.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fauzimaulana.storyapp.core.domain.model.StoryModel
import com.fauzimaulana.storyapp.core.domain.model.UserModel
import com.fauzimaulana.storyapp.core.domain.usecase.StoryUseCase
import com.fauzimaulana.storyapp.core.vo.Resource

class HomeViewModel(private val storyUseCase: StoryUseCase) : ViewModel() {
    fun getUser(): LiveData<UserModel> =
        storyUseCase.getUserPref().asLiveData()

    fun getAllStories(token: String): LiveData<Resource<List<StoryModel>>> =
        storyUseCase.getAllStories(token).asLiveData()
}