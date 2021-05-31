package com.example.hackernews.di.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.viewmodel.ArticleViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ArticleViewModelFactory {
    fun create(url: String): ArticleViewModel
}

fun provideArticleViewModelFactory(
    assistedFactory: ArticleViewModelFactory,
    url: String
): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return assistedFactory.create(url) as T
    }
}
