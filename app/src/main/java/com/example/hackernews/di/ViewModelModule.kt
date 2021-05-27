package com.example.hackernews.di

import androidx.lifecycle.ViewModel
import com.example.hackernews.viewmodel.LoginViewModel
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun loginViewModel(loginViewModel: LoginViewModel): ViewModel
}
