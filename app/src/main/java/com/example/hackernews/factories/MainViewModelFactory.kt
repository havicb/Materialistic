package com.example.hackernews.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.model.repository.NewsRepository
import com.example.hackernews.model.repository.UserRepository
import com.example.hackernews.viewmodel.MainViewModel

class MainViewModelFactory(private val newsRepository: NewsRepository, private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(newsRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}