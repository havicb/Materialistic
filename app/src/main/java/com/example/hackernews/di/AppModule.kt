package com.example.hackernews.di

import android.content.Context
import androidx.room.Room
import com.example.hackernews.common.constants.Constants
import com.example.hackernews.model.database.MaterialisticDatabase
import com.example.hackernews.model.database.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMaterialisticDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        MaterialisticDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideUserDao(db: MaterialisticDatabase): UserDAO {
        return db.userDao()
    }
}