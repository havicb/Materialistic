package com.example.hackernews.di.app

import android.app.Application
import com.example.hackernews.model.database.MaterialisticDatabase
import dagger.Module
import dagger.Provides

@Module
class CacheModule {
    @AppScope
    @Provides
    fun database(application: Application) = MaterialisticDatabase.getInstance(application)
}