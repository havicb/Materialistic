package com.example.hackernews.callbacks

import com.example.hackernews.data.model.NewsM
import java.lang.Exception

interface LoadDataCallback {
    fun onSuccess(news: NewsM)
    fun onFailed(ex: Exception)
}