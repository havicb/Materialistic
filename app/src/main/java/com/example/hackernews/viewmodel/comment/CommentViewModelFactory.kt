package com.example.hackernews.viewmodel.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CommentViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CommentViewModel::class.java)) {
            return CommentViewModel() as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}