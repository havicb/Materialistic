package com.example.hackernews.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hackernews.model.entities.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * from USER ORDER BY id;")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT COUNT(*) from USER")
    fun numUsers() : LiveData<Int>
}