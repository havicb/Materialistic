package com.example.hackernews.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hackernews.model.entities.User

@Database(entities = [User::class], version = 2)
abstract class MaterialisticDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}
