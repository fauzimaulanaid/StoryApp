package com.fauzimaulana.storyapp.view.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fauzimaulana.storyapp.model.UserModel
import com.fauzimaulana.storyapp.model.UserPreference
import kotlinx.coroutines.launch

class SignUpViewModel(private val pref: UserPreference): ViewModel() {
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}