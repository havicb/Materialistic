package com.example.hackernews.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.hackernews.database.entities.UserNews
import com.example.hackernews.database.entities.UserSavedNews

@Dao
interface UserNewsDao : BaseDao<UserNews> {
    @Transaction
    @Query("SELECT * from UserEntity where user_id = 1")
    fun loadSavedPosts(): UserSavedNews?
}
