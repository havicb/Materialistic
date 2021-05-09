package com.example.hackernews.di.activity

import com.example.hackernews.di.app.AppComponent
import com.example.hackernews.view.activities.MainActivity
import com.example.hackernews.view.activities.NewsActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(newsActivity: NewsActivity)
}