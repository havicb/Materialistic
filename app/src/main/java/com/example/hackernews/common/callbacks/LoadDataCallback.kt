package com.example.hackernews.common.callbacks

import com.example.hackernews.model.NewsM
import java.lang.Exception

interface LoadDataCallback {
    fun onSuccess(news: NewsM)
    fun onFailed(ex: Exception)
}