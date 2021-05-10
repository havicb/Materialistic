package com.example.hackernews.di.activity

import com.example.hackernews.view.activities.MainActivity
import com.example.hackernews.view.activities.NewsActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(newsActivity: NewsActivity)
}