package com.example.hackernews.di.activity

import androidx.appcompat.app.AppCompatActivity
import com.example.hackernews.di.presentation.PresentationComponent
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun presentationComponent(): PresentationComponent

    // convention
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance fun activity(activity: AppCompatActivity): Builder
        fun activityModule(activityModule: ActivityModule): Builder // convention
        fun build(): ActivityComponent // activity component, but this needs activity
    }
}
