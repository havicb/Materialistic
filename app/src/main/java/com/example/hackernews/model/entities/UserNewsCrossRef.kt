package com.example.hackernews.model.entities

import androidx.room.Entity
import androidx.room.ForeignKey

// since every user can have multiple saved post, and one post can belong to multiple users we need many to many relationship

@Entity(primaryKeys = ["user_id", "news_id"])
data class UserNewsCrossRef (
    val user_id: Long,
    val news_id: Long
)