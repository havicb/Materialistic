package com.example.hackernews.model.database.dao

import androidx.room.Dao
import com.example.hackernews.model.entities.News

@Dao
interface NewsDao : BaseDao<News> {
}