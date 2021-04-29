package com.example.hackernews.model.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserSavedNews(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entity = News::class,
        entityColumn = "news_id",
        associateBy = Junction(UserNews::class)
    )
    val list: List<News>
)