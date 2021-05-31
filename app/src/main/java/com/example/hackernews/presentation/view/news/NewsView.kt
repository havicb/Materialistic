package com.example.hackernews.presentation.view.news

import com.example.hackernews.core.helpers.Helper
import com.example.hackernews.domain.entities.News
import java.io.Serializable

class NewsView(
    val id: String,
    val by: String,
    val url: String,
    val score: String,
    val title: String,
    val time: String,
    val commentsNum: String
) : Serializable

fun News.toView() = NewsView(
    id.toString(),
    by,
    url,
    score.toString(),
    title,
    Helper.formatDate(time),
    kids.size.toString()
)
