package com.example.hackernews.di.app

import android.app.Application
import android.os.Handler
import android.os.Looper
import com.example.hackernews.core.helpers.Dispatcher
import com.example.hackernews.data.comments.CommentsRepository
import com.example.hackernews.data.news.NewsRepository
import com.example.hackernews.data.service.NewsService
import com.example.hackernews.data.user.UserRepository
import com.example.hackernews.database.MaterialisticDatabase
import dagger.Module
import dagger.Provides
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Module
class AppModule(private val application: Application) {

    @AppScope
    @Provides
    fun provideApp() = application

    @Provides
    fun executor() = Executors.newSingleThreadExecutor()

    @Provides
    fun mainThreadHandler() = Handler(Looper.getMainLooper())

    @Provides
    fun newsRepository(
        materialisticDatabase: MaterialisticDatabase,
        dispatcher: Dispatcher,
        newsService: NewsService
    ): NewsRepository {
        return NewsRepository(
            newsService,
            materialisticDatabase.newsDao(),
            dispatcher
        )
    }

    @Provides
    fun commentsRepository(newsService: NewsService) = CommentsRepository(newsService)

    @AppScope
    @Provides
    fun DISPATCHER(executorService: ExecutorService, mainThreadHandler: Handler) =
        Dispatcher(executorService, mainThreadHandler)

    @Provides
    fun userRepository(
        materialisticDatabase: MaterialisticDatabase,
        dispatcher: Dispatcher
    ): UserRepository {
        return UserRepository(
            materialisticDatabase.userDao(),
            materialisticDatabase.userNewsDao(),
            dispatcher
        )
    }
}
