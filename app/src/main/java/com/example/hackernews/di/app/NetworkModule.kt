package com.example.hackernews.di.app

import com.example.hackernews.common.constants.Constants
import com.example.hackernews.data.service.NewsService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

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
}