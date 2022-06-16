package com.fauzimaulana.storyapp

import android.app.Application
import com.fauzimaulana.storyapp.di.repositoryModule
import com.fauzimaulana.storyapp.di.useCaseModule
import com.fauzimaulana.storyapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}