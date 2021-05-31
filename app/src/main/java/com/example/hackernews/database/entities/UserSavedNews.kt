package com.example.hackernews.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserSavedNews(
    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "user_id",
        entity = NewsEntity::class,
        entityColumn = "news_id",
        associateBy = Junction(UserNews::class)
    )
    val list: List<NewsEntity>
)
