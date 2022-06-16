package com.fauzimaulana.storyapp.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.fauzimaulana.storyapp.core.data.source.remote.network.ApiService
import com.fauzimaulana.storyapp.core.domain.model.UserPreference
import com.fauzimaulana.storyapp.core.domain.repository.IUserPreference
import com.fauzimaulana.storyapp.core.domain.usecase.StoryInteractor
import com.fauzimaulana.storyapp.core.domain.usecase.StoryUseCase
import com.fauzimaulana.storyapp.view.login.LoginViewModel
import com.fauzimaulana.storyapp.view.main.MainViewModel
import com.fauzimaulana.storyapp.view.main.ui.account.AccountViewModel
import com.fauzimaulana.storyapp.view.signup.SignUpViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val repositoryModule = module {
    single<IUserPreference> { UserPreference(androidContext().datastore) }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val useCaseModule = module {
    factory<StoryUseCase> { StoryInteractor(get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { AccountViewModel(get()) }
    viewModel { MainViewModel(get()) }
}