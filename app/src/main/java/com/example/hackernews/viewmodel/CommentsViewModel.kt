package com.example.hackernews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hackernews.model.network.Comment
import com.example.hackernews.model.repository.CommentsRepository


class CommentsViewModel(private val commentsRepository: CommentsRepository) : ViewModel() {
    private var _comments = arrayListOf<Comment>()
    val comments = MutableLiveData<List<Comment>>()

    init {
        fetchComments()
    }

    private fun fetchComments() {
        _comments = commentsRepository.fetchComments()
        comments.value = _comments
    }
}