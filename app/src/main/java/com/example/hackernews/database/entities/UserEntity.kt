package com.example.hackernews.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var user_id: Long = 0,
    @ColumnInfo(name = "user_username")
    val username: String,
    @ColumnInfo(name = "user_password")
    val password: String,
    @ColumnInfo(name = "user_token")
    val token: String,
    @ColumnInfo(name = "is_logged", defaultValue = false.toString())
    var isLogged: Boolean,
)
