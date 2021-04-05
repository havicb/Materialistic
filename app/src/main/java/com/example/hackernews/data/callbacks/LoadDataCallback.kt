package com.example.hackernews.data.callbacks

import com.example.hackernews.data.model.NewsM
import java.lang.Exception

interface LoadDataCallback {
    fun onSuccess(news: NewsM)
    fun onFailed(ex: Exception)
}