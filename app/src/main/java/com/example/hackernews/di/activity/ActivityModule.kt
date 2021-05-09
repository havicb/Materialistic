package com.example.hackernews.di.activity

import android.app.Activity
import android.os.Handler
import android.os.Looper
import com.example.hackernews.common.helpers.Dispatcher
import com.example.hackernews.data.service.NewsService
import com.example.hackernews.di.app.AppComponent
import com.example.hackernews.di.app.AppScope
import com.example.hackernews.model.repository.CommentsRepository
import com.example.hackernews.model.repository.NewsRepository
import com.example.hackernews.model.repository.UserRepository
import dagger.Module
import dagger.Provides
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Module
class ActivityModule(
    private val activity: Activity,
    private val appComponent: AppComponent
) {

    @ActivityScope
    @Provides
    fun activity() = activity

    @Provides
    fun newsService() = appComponent.provideNewsService()

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
    fun newsRepository(dispatcher: Dispatcher, newsService: NewsService): NewsRepository {
        return NewsRepository(
            newsService,
            appComponent.provideDatabase().newsDao(),
            dispatcher
        )
    }

    @Provides
    fun userRepository(dispatcher: Dispatcher): UserRepository {
        return UserRepository(
            appComponent.provideDatabase().userDao(),
            appComponent.provideDatabase().userNewsDao(),
            dispatcher
        )
    }

    @Provides
    fun commentsRepository() = CommentsRepository(appComponent.provideNewsService())

}