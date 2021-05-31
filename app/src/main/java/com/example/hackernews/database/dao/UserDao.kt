package com.example.hackernews.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.hackernews.database.entities.UserEntity

@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("SELECT * from UserEntity where user_username LIKE :username AND user_password LIKE :password")
    fun retrieveUser(username: String, password: String): UserEntity

    @Query("SELECT COUNT(*) from UserEntity where user_username = :username and user_password = :password")
    fun hasUserExists(username: String, password: String): Int

    @Query("SELECT * from UserEntity where is_logged = 1")
    fun fetchLoggedUser(): UserEntity?
}
