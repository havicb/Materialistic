package com.example.hackernews.model.repository

import com.example.hackernews.data.service.NewsService
import com.example.hackernews.model.entities.Comment

class CommentsRepository(private val newsApi: NewsService) {

    fun fetchComments(): List<Comment> {
        val comments = ArrayList<Comment>()
        for (i in 0..5) {
            comments.add(Comment(i, "belmin", 123321, "Dummy comment", 1321312, "comment"))
        }
        return comments
    }
}