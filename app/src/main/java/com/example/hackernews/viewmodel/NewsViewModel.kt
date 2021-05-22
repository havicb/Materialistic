package com.example.hackernews.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.hackernews.database.entities.News

class NewsViewModel(_selectedNews: News) : BaseViewModel() {

    val selectedNews = MutableLiveData<News>()

    init {
        selectedNews.value = _selectedNews
    }
}
