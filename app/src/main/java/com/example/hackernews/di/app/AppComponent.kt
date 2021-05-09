package com.example.hackernews.di.app

import android.app.Application
import com.example.hackernews.common.helpers.Dispatcher
import com.example.hackernews.data.service.NewsService
import com.example.hackernews.model.database.MaterialisticDatabase
import dagger.Component
import dagger.Provides

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun provideApp(): Application
    fun provideNewsService(): NewsService
    fun provideDatabase(): MaterialisticDatabase
}