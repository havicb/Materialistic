package com.example.hackernews.di.activity

import android.app.Activity
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
class ActivityModule(
    private val activity: Activity,
) {

    @Provides
    fun activity() = activity

    @ActivityScope
    @Provides
    fun executor() = Executors.newSingleThreadExecutor()

    @ActivityScope
    @Provides
    fun mainThreadHandler() = Handler(Looper.getMainLooper())

    @ActivityScope
    @Provides
    fun DISPATCHER(executorService: ExecutorService, mainThreadHandler: Handler) =
        Dispatcher(executorService, mainThreadHandler)

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

    @Provides
    fun commentsRepository(newsService: NewsService) = CommentsRepository(newsService)
}
