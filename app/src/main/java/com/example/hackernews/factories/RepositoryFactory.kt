package com.example.hackernews.factories

import android.content.Context
import com.example.hackernews.common.constants.Constants
import com.example.hackernews.data.service.NewsService
import com.example.hackernews.model.database.MaterialisticDatabase
import com.example.hackernews.model.entities.User
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

    private lateinit var database: MaterialisticDatabase

    private val newsService by lazy {
        retrofit.create(NewsService::class.java)
    }

    val newsRepository: NewsRepository by lazy {
        NewsRepository(newsService)
    }

    // i had to provide context for room database instance without using dagger and since you can not have constructor in object i find out just for this solution
    //  probably this is one bad solution, because there is no importance of calling setDatabase(), so it really ease to make NPE when you accessing database field
    // but i think we will get to that soon :D
    fun setDatabase(context: Context) {
        database = MaterialisticDatabase.getInstance(context)
    }

    fun getNewsRepository() : UserRepository {
        return UserRepository(database.userDao())
    }

    val commentsRepository: CommentsRepository by lazy {
        CommentsRepository(newsService)
    }
}
