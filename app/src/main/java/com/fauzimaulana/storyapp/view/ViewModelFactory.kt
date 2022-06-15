package com.fauzimaulana.storyapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fauzimaulana.storyapp.di.Injection
import com.fauzimaulana.storyapp.domain.usecase.StoryUseCase
import com.fauzimaulana.storyapp.view.login.LoginViewModel
import com.fauzimaulana.storyapp.view.main.MainViewModel
import com.fauzimaulana.storyapp.view.main.ui.account.AccountViewModel
import com.fauzimaulana.storyapp.view.main.ui.home.HomeViewModel
import com.fauzimaulana.storyapp.view.signup.SignUpViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val storyUseCase: StoryUseCase): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(storyUseCase) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(storyUseCase) as T
            }
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> {
                AccountViewModel(storyUseCase) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(storyUseCase) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(storyUseCase) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideStoryUseCase(context))
            }
    }
}