package com.fauzimaulana.storyapp.view.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fauzimaulana.storyapp.core.domain.model.AddNewStoryModel
import com.fauzimaulana.storyapp.core.domain.model.UserModel
import com.fauzimaulana.storyapp.core.domain.usecase.StoryUseCase
import com.fauzimaulana.storyapp.core.vo.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadStoryViewModel(private val storyUseCase: StoryUseCase): ViewModel() {
    fun getUser(): LiveData<UserModel> =
        storyUseCase.getUserPref().asLiveData()

    fun uploadStory(file: MultipartBody.Part, description: RequestBody, token: String): LiveData<Resource<AddNewStoryModel>> =
        storyUseCase.uploadStory(file, description, token).asLiveData()
}