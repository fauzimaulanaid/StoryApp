package com.fauzimaulana.storyapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fauzimaulana.storyapp.model.UserPreference
import com.fauzimaulana.storyapp.view.login.LoginViewModel
import com.fauzimaulana.storyapp.view.main.MainViewModel
import com.fauzimaulana.storyapp.view.main.ui.account.AccountViewModel
import com.fauzimaulana.storyapp.view.main.ui.home.HomeViewModel
import com.fauzimaulana.storyapp.view.signup.SignUpViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val pref: UserPreference): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> {
                AccountViewModel(pref) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}