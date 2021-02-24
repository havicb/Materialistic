package com.example.hackernews.news

import java.io.Serializable

data class News(val newsId: Int, val newsTitle: String, val newsUrl: String, val newsPublisher: String,
           val newsTimePublished: String) : Serializable  {

}