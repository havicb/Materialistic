package com.example.hackernews.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.hackernews.model.entities.UserNewsCrossRef
import com.example.hackernews.model.entities.UserSavedNews

@Dao
interface UserNewsCrossRefDao : BaseDao<UserNewsCrossRef> {

    @Transaction
    @Query("SELECT * from User")
    fun loadSavedPosts() : LiveData<UserSavedNews>
}