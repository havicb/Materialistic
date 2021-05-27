package com.example.hackernews.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.viewmodel.FeedbackViewModel
import com.example.hackernews.viewmodel.LoginViewModel
import com.example.hackernews.viewmodel.MainViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    var loginViewModelProvider: Provider<LoginViewModel>,
    var mainViewModelProvider: Provider<MainViewModel>,
    var feedbackViewModelProvider: Provider<FeedbackViewModel>,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            LoginViewModel::class.java -> loginViewModelProvider.get() as T
            MainViewModel::class.java -> mainViewModelProvider.get() as T
            FeedbackViewModel::class.java -> feedbackViewModelProvider.get() as T
            else -> throw IllegalArgumentException("Unsupported view model class $modelClass")
        }
    }
}
