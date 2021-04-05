package com.example.hackernews.data.callbacks

import com.example.hackernews.data.model.Comment

interface LoadCommentCallback {
    fun onCommentLoaded(comment: Comment)
    fun onFailedToLoad(ex: Exception)
}