package com.example.hackernews.di.app

import android.app.Application
import com.example.hackernews.database.MaterialisticDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {
    @Provides
    fun database(application: Application) = MaterialisticDatabase.getInstance(application)
}
