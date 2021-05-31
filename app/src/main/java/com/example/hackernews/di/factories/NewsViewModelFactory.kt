package com.example.hackernews.di.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.database.entities.News
import com.example.hackernews.viewmodel.NewsViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface NewsViewModelFactory {
    fun create(selectedNews: News): NewsViewModel
}

fun provideNewsVMFactory(
    assistedFactory: NewsViewModelFactory,
    selectedNews: News
): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return assistedFactory.create(selectedNews) as T
    }
}
