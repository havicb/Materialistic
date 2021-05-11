package com.example.hackernews.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.hackernews.model.entities.UserNews
import com.example.hackernews.model.entities.UserSavedNews

@Dao
interface UserNewsDao : BaseDao<UserNews> {
    @Transaction
    @Query("SELECT * from User where user_id = 1")
    fun loadSavedPosts(): UserSavedNews?
}