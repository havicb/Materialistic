package com.example.hackernews.viewmodel

import android.webkit.WebViewClient
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticleViewModel(initialUrl: String) : ViewModel() {

    private var newsUrl = MutableLiveData<String>()
    private var webViewClient = MutableLiveData<WebViewClient>()

    val url: LiveData<String> get() = newsUrl
    val client: LiveData<WebViewClient> get() = webViewClient

    init {
        newsUrl.value = initialUrl
        webViewClient.value = WebViewClient()
    }
}