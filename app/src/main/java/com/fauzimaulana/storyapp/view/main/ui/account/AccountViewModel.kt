package com.fauzimaulana.storyapp.view.main.ui.account

import androidx.lifecycle.*
import com.fauzimaulana.storyapp.model.UserModel
import com.fauzimaulana.storyapp.model.UserPreference
import kotlinx.coroutines.launch

class AccountViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}