package com.example.hackernews.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.hackernews.model.entities.UserNews
import com.example.hackernews.model.entities.UserSavedNews

@Dao
interface UserNewsDao : BaseDao<UserNews> {
    // do I need this dao at all?
    // because every operation in this dao is correlated for particular user? i could provide function in userDao, but i don t know is it good or bad practice

    @Transaction
    @Query("SELECT * from User where user_id = :userId")
    fun loadSavedPosts(userId: Long): UserSavedNews?
}