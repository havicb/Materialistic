package com.example.hackernews.factories

import com.example.hackernews.common.constants.Api
import com.example.hackernews.data.service.NewsService
import com.example.hackernews.model.repository.NewsRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepositoryFactory {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val newsService by lazy {
        retrofit.create(NewsService::class.java)
    }

    val newsRepository: NewsRepository by lazy {
        NewsRepository(newsService)
    }
}
