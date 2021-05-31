package com.example.hackernews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hackernews.data.comments.CommentsRepository
import com.example.hackernews.database.entities.Comment
import com.example.hackernews.database.entities.News
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class CommentsViewModel @AssistedInject constructor(
    private val commentsRepository: CommentsRepository,
    @Assisted selectedNews: News
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
