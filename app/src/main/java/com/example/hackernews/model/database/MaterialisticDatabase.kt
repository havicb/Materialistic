package com.example.hackernews.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hackernews.model.database.dao.NewsDao
import com.example.hackernews.model.database.dao.UserDao
import com.example.hackernews.model.database.dao.UserNewsDao
import com.example.hackernews.model.entities.Comment
import com.example.hackernews.model.entities.News
import com.example.hackernews.model.entities.User
import com.example.hackernews.model.entities.UserNews

@Database(
    entities = [User::class, Comment::class, News::class, UserNews::class],
    version = 10,
    exportSchema = false
)
abstract class MaterialisticDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun newsDao(): NewsDao
    abstract fun userNewsDao(): UserNewsDao

    companion object {
        @Volatile
        private var INSTANCE: MaterialisticDatabase? = null

        fun getInstance(context: Context): MaterialisticDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MaterialisticDatabase::class.java,
                    "materialistic_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}