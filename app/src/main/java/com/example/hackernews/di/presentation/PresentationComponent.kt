package com.example.hackernews.di.presentation

import com.example.hackernews.di.vm.ViewModelModule
import com.example.hackernews.view.activities.MainActivity
import com.example.hackernews.view.activities.NewsActivity
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [ViewModelModule::class])
interface PresentationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(newsActivity: NewsActivity)
}
