package com.example.hackernews.model.repository

import com.example.hackernews.data.service.NewsService
import com.example.hackernews.model.network.Comment

typealias CommentsCallBack = (Comment?, ApiError?) -> Unit

class CommentsRepository(private val newsApi: NewsService) {

    fun fetchComments(): ArrayList<Comment> {
        val comments = ArrayList<Comment>()
        for (i in 0..5) {
            comments.add(Comment("beli", i, null, 1321312, "Dummy comment", 123123, "comment"))
        }
        return comments
    }
}