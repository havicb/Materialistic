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

    @AppScope
    @Provides
    fun httpLoggingInterceptor() = HttpLoggingInterceptor()

    @AppScope
    @Provides
    fun client(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY;
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build();
    }

    @AppScope
    @Provides
    fun retrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @AppScope
    @Provides
    fun newsService(retrofit: Retrofit) = retrofit.create(NewsService::class.java)

    @AppScope
    @Provides
    fun database() = MaterialisticDatabase.getInstance(application)
}