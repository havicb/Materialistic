package com.example.hackernews.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.hackernews.database.entities.NewsEntity

@Dao
interface NewsDao : BaseDao<NewsEntity> {

    @Query("SELECT * from NewsEntity where newsType=:newsType")
    fun loadStories(newsType: String): List<NewsEntity>?
}
