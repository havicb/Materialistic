package com.example.hackernews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hackernews.model.entities.Comment
import com.example.hackernews.model.entities.News
import com.example.hackernews.model.repository.CommentsRepository

class CommentsViewModel(
    private val commentsRepository: CommentsRepository,
    private val selectedNews: News
) : BaseViewModel() {

    private var _comments = arrayListOf<Comment>()
    private var _commentAreWaitingToBeLoaded = MutableLiveData(true)

    val comments = MutableLiveData<List<Comment>>()
    val commentsAreWaitingToBeLoaded: LiveData<Boolean>
        get() = _commentAreWaitingToBeLoaded

    init {
        fetchComments(selectedNews)
    }

    fun fetchComments(news: News) {
        commentsRepository.fetchComments(news) { fetchedComment ->
            _commentAreWaitingToBeLoaded.value = false
            _comments.add(fetchedComment!!)
            comments.value = _comments
        }
    }
}