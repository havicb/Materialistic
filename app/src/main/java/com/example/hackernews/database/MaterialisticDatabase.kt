package com.example.hackernews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hackernews.database.dao.NewsDao
import com.example.hackernews.database.dao.UserDao
import com.example.hackernews.database.dao.UserNewsDao
import com.example.hackernews.database.entities.Comment
import com.example.hackernews.database.entities.News
import com.example.hackernews.database.entities.User
import com.example.hackernews.database.entities.UserNews

@Database(
    entities = [User::class, Comment::class, News::class, UserNews::class],
    version = 11,
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
