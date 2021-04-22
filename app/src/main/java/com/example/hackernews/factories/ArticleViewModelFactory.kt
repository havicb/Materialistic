package com.example.hackernews.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.viewmodel.ArticleViewModel

class ArticleViewModelFactory(private val newsUrl: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            return ArticleViewModel(newsUrl) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}