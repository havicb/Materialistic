package com.example.hackernews.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.hackernews.database.entities.News
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class NewsViewModel @AssistedInject constructor(
    @Assisted private val _selectedNews: News
) : BaseViewModel() {

    val selectedNews = MutableLiveData<News>()

    init {
        selectedNews.value = _selectedNews
    }
}
