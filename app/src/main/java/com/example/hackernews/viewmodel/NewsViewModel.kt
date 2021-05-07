package com.example.hackernews.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.hackernews.factories.CommentViewModelFactory
import com.example.hackernews.model.entities.Comment
import com.example.hackernews.model.entities.News

class NewsViewModel(_selectedNews: News) : BaseViewModel() {

    val selectedNews = MutableLiveData<News>()

    init {
        selectedNews.value = _selectedNews
    }
}