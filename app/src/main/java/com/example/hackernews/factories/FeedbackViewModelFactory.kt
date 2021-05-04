package com.example.hackernews.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.viewmodel.FeedbackViewModel

class FeedbackViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedbackViewModel::class.java)) {
            return FeedbackViewModel() as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}