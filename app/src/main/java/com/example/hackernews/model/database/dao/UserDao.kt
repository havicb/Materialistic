package com.example.hackernews.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.hackernews.model.entities.User

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * from USER where user_username LIKE :username AND user_password LIKE :password")
    fun retrieveUser(username: String, password: String): User

    @Query("SELECT COUNT(*) from user where user_username = :username and user_password = :password")
    fun hasUserExists(username: String, password: String): Int

    @Query("SELECT * from user where is_logged = 1")
    fun fetchLoggedUser(): User?
}