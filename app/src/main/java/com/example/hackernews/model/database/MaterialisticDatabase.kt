package com.example.hackernews.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hackernews.model.entities.User

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class MaterialisticDatabase : RoomDatabase() {

    abstract fun userDao() : UserDAO

    companion object {
        @Volatile
        private var INSTANCE : MaterialisticDatabase? = null

        fun getInstance(context: Context) : MaterialisticDatabase {
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



