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
        associateBy = Junction(UserNewsCrossRef::class,
        parentColumn = "user_id",
        entityColumn = "news_id")
    )
    val list: List<News>
)