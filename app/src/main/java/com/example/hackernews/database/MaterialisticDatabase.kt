package com.example.hackernews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hackernews.database.dao.NewsDao
import com.example.hackernews.database.dao.UserDao
import com.example.hackernews.database.dao.UserNewsDao
import com.example.hackernews.database.entities.CommentEntity
import com.example.hackernews.database.entities.NewsEntity
import com.example.hackernews.database.entities.UserEntity
import com.example.hackernews.database.entities.UserNews

@Database(
    entities = [UserEntity::class, CommentEntity::class, NewsEntity::class, UserNews::class],
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
