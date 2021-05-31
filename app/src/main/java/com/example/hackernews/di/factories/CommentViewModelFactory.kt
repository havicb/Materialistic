package com.example.hackernews.di.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.database.entities.NewsEntity
import com.example.hackernews.domain.entities.News
import com.example.hackernews.viewmodel.CommentsViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface CommentViewModelFactory {
    fun create(selectedNewsEntity: News): CommentsViewModel
}

fun provideCommentVMFactory(
    commentViewModelFactory: CommentViewModelFactory,
    selectedNewsEntity: News
): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return commentViewModelFactory.create(selectedNewsEntity) as T
    }
}