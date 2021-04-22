package com.example.hackernews.factories

import com.example.hackernews.BaseApplication
import com.example.hackernews.common.constants.Constants
import com.example.hackernews.data.service.NewsService
import com.example.hackernews.model.database.MaterialisticDatabase
import com.example.hackernews.model.repository.CommentsRepository
import com.example.hackernews.model.repository.NewsRepository
import com.example.hackernews.model.repository.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepositoryFactory {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val database: MaterialisticDatabase by lazy {
        MaterialisticDatabase.getInstance(BaseApplication.context)
    }

    private val newsService by lazy {
        retrofit.create(NewsService::class.java)
    }

    val newsRepository: NewsRepository by lazy {
        NewsRepository(newsService)
    }

    val userRepository: UserRepository by lazy {
        UserRepository(database.userDao())
    }

    val commentsRepository: CommentsRepository by lazy {
        CommentsRepository(newsService)
    }
}
