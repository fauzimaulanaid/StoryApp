package com.fauzimaulana.storyapp.view.main.ui.home

import androidx.lifecycle.ViewModel
import com.fauzimaulana.storyapp.core.domain.usecase.StoryUseCase

class HomeViewModel(private val storyUseCase: StoryUseCase) : ViewModel() {}