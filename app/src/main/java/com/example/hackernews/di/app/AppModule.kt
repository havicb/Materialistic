package com.example.hackernews.di.app

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {
    @AppScope
    @Provides
    fun provideApp() = application
}
