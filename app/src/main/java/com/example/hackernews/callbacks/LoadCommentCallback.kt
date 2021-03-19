package com.example.hackernews.callbacks

import com.example.hackernews.models.Comment

interface LoadCommentCallback {
    fun onCommentLoaded(comment: Comment)
    fun onFailedToLoad(ex: Exception)
}