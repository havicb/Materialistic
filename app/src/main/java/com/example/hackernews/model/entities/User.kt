package com.example.hackernews.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name="user_username")
    val username: String,

    @ColumnInfo(name="user_password")
    val password: String,

    @ColumnInfo(name="user_token")
    val token: String,

    @ColumnInfo(name = "is_logged", defaultValue = 0.toString())
    val isLogged: Int,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
