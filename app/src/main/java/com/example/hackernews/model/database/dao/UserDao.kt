package com.example.hackernews.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.hackernews.model.entities.User
import com.example.hackernews.model.entities.UserSavedNews

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * from USER where user_username LIKE :username AND user_password LIKE :password")
    fun retrieveUser(username: String, password: String): User

    @Query("SELECT COUNT(*) from user where user_username = :username and user_password = :password")
    fun hasUserExists(username: String, password: String): Int

    @Query("SELECT * from user where is_logged = 1")
    fun fetchLoggedUser(): User? // should I use live data here?

    @Transaction
    @Query("SELECT * from user where user_id = :userId")
    fun loadSavedPosts(userId: Long): LiveData<UserSavedNews>
}