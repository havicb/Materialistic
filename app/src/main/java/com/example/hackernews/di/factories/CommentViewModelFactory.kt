package com.example.hackernews.di.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.database.entities.News
import com.example.hackernews.viewmodel.CommentsViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface CommentViewModelFactory {
    fun create(selectedNews: News): CommentsViewModel
}

fun provideCommentVMFactory(
    commentViewModelFactory: CommentViewModelFactory,
    selectedNews: News
): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return commentViewModelFactory.create(selectedNews) as T
    }
}