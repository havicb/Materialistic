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

    @Query("UPDATE User set is_logged = 1 where user_username = :username AND user_password = :password")
    suspend fun logIn(username: String, password: String)

    @Query("SELECT * from USER order by id")
    fun getAllUser() : Flow<List<User>>

    @Query("SELECT * from user where user_username =:username and user_password = :password")
    fun getLoggedUser(username: String, password: String) : LiveData<User?>
}