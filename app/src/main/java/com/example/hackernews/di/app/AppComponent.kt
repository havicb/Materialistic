package com.example.hackernews.di.app

import com.example.hackernews.di.activity.ActivityComponent
import com.example.hackernews.di.activity.ActivityModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class, NetworkModule::class, CacheModule::class])
interface AppComponent {
    fun newActivityComponent(module: ActivityModule): ActivityComponent
}