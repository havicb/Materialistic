package com.example.hackernews.di.app

import android.app.Application
import android.os.Handler
import android.os.Looper
import com.example.hackernews.common.constants.Constants
import com.example.hackernews.common.helpers.Dispatcher
import com.example.hackernews.data.service.NewsService
import com.example.hackernews.model.database.MaterialisticDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Module
class AppModule(private val application: Application) {
    @AppScope
    @Provides
    fun provideApp() = application
}