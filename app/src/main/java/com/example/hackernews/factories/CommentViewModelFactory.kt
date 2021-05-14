package com.example.hackernews.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.model.entities.News
import com.example.hackernews.model.repository.CommentsRepository
import com.example.hackernews.viewmodel.CommentsViewModel

class CommentViewModelFactory(private val commentsRepository: CommentsRepository, private val selectedNews: News) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentsViewModel::class.java)) {
            return CommentsViewModel(commentsRepository, selectedNews) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}