package com.example.hackernews.viewmodel.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.model.repository.MaterialisticRepository
import com.example.hackernews.viewmodel.article.ArticleFragmentViewModel
import java.lang.IllegalArgumentException

class UserViewModelFactory(private val repository: MaterialisticRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
        }
    }
