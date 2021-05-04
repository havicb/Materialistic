package com.example.hackernews.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.hackernews.model.entities.Comment
import com.example.hackernews.model.repository.CommentsRepository


class CommentsViewModel(private val commentsRepository: CommentsRepository) : BaseViewModel() {
    private var _comments = listOf<Comment>()
    val comments = MutableLiveData<List<Comment>>()

    init {
        fetchComments()
    }

    private fun fetchComments() {
        _comments = commentsRepository.fetchComments()
        comments.value = _comments
    }
}