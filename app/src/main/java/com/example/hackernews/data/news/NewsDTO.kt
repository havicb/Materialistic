package com.example.hackernews.data.news

data class NewsDTO(
    var id: Long,
    var by: String,
    var descendants: Int,
    var kids: List<Int>?,
    var score: Int,
    var time: Long,
    var title: String,
    var type: String,
    var url: String,
    var newsType: String
)
