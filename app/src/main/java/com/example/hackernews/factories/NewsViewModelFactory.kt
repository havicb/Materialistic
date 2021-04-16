package com.example.hackernews.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.model.network.News
import com.example.hackernews.viewmodel.NewsViewModel

class NewsViewModelFactory(private val selectedNews: News) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(selectedNews) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}