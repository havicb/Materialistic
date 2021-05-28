package com.example.hackernews.di.app

import com.example.hackernews.di.activity.ActivityComponent
import dagger.Component

@AppScope
@Component(modules = [AppModule::class, NetworkModule::class, CacheModule::class])
interface AppComponent {
    fun newActivityComponentBuilder(): ActivityComponent.Builder
}
