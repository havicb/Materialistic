package com.example.hackernews.di.vm

import androidx.lifecycle.ViewModel
import com.example.hackernews.viewmodel.FeedbackViewModel
import com.example.hackernews.viewmodel.LoginViewModel
import com.example.hackernews.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun loginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedbackViewModel::class)
    abstract fun feedbackViewModel(feedbackViewModel: FeedbackViewModel): ViewModel
}
