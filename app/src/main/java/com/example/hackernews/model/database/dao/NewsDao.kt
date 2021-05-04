package com.example.hackernews.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.hackernews.common.enums.NewsDataType
import com.example.hackernews.model.entities.News

@Dao
interface NewsDao : BaseDao<News> {

    @Query("SELECT * from news where newsType=:newsType")
    fun loadStories(newsType: String) : List<News>?
}