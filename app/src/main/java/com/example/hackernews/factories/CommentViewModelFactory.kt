package com.example.hackernews.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.model.repository.CommentsRepository
import com.example.hackernews.viewmodel.CommentsViewModel

class CommentViewModelFactory(private val commentsRepository: CommentsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentsViewModel::class.java)) {
            return CommentsViewModel(commentsRepository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}