package com.example.hackernews.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.hackernews.database.entities.News

@Dao
interface NewsDao : BaseDao<News> {

    @Query("SELECT * from news where newsType=:newsType")
    fun loadStories(newsType: String): List<News>?
}
