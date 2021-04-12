package com.example.hackernews.common.callbacks

import com.example.hackernews.model.network.Comment

interface LoadCommentCallback {
    fun onCommentLoaded(comment: Comment)
    fun onFailedToLoad(ex: Exception)
}