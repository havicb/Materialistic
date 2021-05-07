package com.example.hackernews.factories

import android.os.Handler
import android.os.Looper
import com.example.hackernews.BaseApplication
import com.example.hackernews.common.constants.Constants
import com.example.hackernews.common.helpers.Dispatcher
import com.example.hackernews.data.service.NewsService
import com.example.hackernews.model.database.MaterialisticDatabase
import com.example.hackernews.model.repository.CommentsRepository
import com.example.hackernews.model.repository.NewsRepository
import com.example.hackernews.model.repository.UserRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object RepositoryFactory {

    private val HttpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor)
        .build();

    private val retrofit: Retrofit by lazy {
        HttpLoggingInterceptor.level = BODY;
        Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private val DISPATCHER: Dispatcher by lazy {
        Dispatcher(executor, mainThreadHandler)
    }

    private val database: MaterialisticDatabase by lazy {
        MaterialisticDatabase.getInstance(BaseApplication.context)
    }

    private val newsService by lazy {
        retrofit.create(NewsService::class.java)
    }

    val newsRepository: NewsRepository by lazy {
        NewsRepository(newsService, database.newsDao(), DISPATCHER)
    }

    val userRepository: UserRepository by lazy {
        UserRepository(database.userDao(), database.userNewsDao(), DISPATCHER)
    }

    val commentsRepository: CommentsRepository by lazy {
        CommentsRepository(newsService)
    }

    private val executor: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    private val mainThreadHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }
}
