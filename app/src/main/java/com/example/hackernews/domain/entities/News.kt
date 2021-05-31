package com.example.hackernews.domain.entities

import com.example.hackernews.data.news.NewsDTO
import com.example.hackernews.database.entities.NewsEntity
import java.io.Serializable

data class News(
    var id: Long,
    var by: String,
    var descendants: Int,
    var idToShow: Int,
    var kids: List<Int>,
    var score: Int,
    var time: Long,
    var title: String,
    var type: String,
    var url: String,
    var newsType: String
) : Serializable

fun NewsDTO.toDomain() = News(
    id, by, descendants, 1, kids ?: arrayListOf(), score, time, title, type, url ?: "", newsType ?: ""
)

fun NewsDTO.toEntity() = NewsEntity(
    id, by, descendants, score, time, title, type, url, newsType
)

fun NewsEntity.toDomain() = News(
    id, by, descendants, 1, arrayListOf(), score, time, title, type, url, newsType
)
