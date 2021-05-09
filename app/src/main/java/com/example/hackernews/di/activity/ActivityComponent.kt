package com.example.hackernews.di.activity

import android.app.Activity
import com.example.hackernews.common.helpers.Dispatcher
import com.example.hackernews.data.service.NewsService
import com.example.hackernews.di.app.AppComponent
import com.example.hackernews.model.repository.CommentsRepository
import com.example.hackernews.model.repository.NewsRepository
import com.example.hackernews.model.repository.UserRepository
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun activity() : Activity
    fun newsService() : NewsService
    fun newsRepository(): NewsRepository
    fun userRepository(): UserRepository
    fun commentsRepository(): CommentsRepository
}