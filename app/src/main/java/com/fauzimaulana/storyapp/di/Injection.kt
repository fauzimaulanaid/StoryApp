package com.fauzimaulana.storyapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.fauzimaulana.storyapp.domain.model.UserPreference
import com.fauzimaulana.storyapp.domain.usecase.StoryInteractor
import com.fauzimaulana.storyapp.domain.usecase.StoryUseCase

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Injection {
    private fun providePreference(context: Context): UserPreference {
        return UserPreference.getInstance(context.datastore)
    }

    fun provideStoryUseCase(context: Context): StoryUseCase {
        val preference = providePreference(context)
        return StoryInteractor(preference)
    }
}