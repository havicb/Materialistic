package com.example.hackernews.callbacks

interface CallBackLoadPostIdDAO {
    suspend fun onLoadedPost(postId: Int)
    fun onFailed(ex: Exception)
}