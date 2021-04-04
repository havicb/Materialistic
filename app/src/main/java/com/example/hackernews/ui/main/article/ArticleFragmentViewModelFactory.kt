package com.example.hackernews.ui.main.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ArticleFragmentViewModelFactory(private val newsUrl: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ArticleFragmentViewModel::class.java)) {
            return ArticleFragmentViewModel(newsUrl) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}