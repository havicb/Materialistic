package com.example.hackernews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hackernews.model.network.News

class NewsViewModel(private val _selectedNews: News) : ViewModel() {

    val selectedNews = MutableLiveData<News>()

    init {
        selectedNews.value = _selectedNews
    }
}