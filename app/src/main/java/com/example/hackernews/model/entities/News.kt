package com.example.hackernews.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class News(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "news_id") // i will use api ID for PK
    var id: Long,
    var by: String,
    @ColumnInfo(name = "comments_number")
    var descendants: Int,
    @ColumnInfo(name = "public_id")
    var idToShow: Int,
    var score: Int,
    var time: Long,
    var title: String,
    var type: String,
    @ColumnInfo(defaultValue = "")
    var url: String
) : Serializable