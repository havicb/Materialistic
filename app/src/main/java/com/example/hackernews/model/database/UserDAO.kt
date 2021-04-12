package com.example.hackernews.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hackernews.model.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * from USER order by id")
    fun getAllUser() : Flow<List<User>>

    @Query("SELECT * from USER where id = :user_id")
    fun getUserByID(user_id: Int) : LiveData<User>

}