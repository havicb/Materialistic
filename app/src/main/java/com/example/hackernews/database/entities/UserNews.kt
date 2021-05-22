package com.example.hackernews.database.entities

import androidx.room.Entity

// since every user can have multiple saved post, and one post can belong to multiple users we need many to many relationship
@Entity(primaryKeys = ["user_id", "news_id"])
data class UserNews(
    val user_id: Long,
    val news_id: Long
)
