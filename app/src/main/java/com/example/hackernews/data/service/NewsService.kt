package com.example.hackernews.data.service

import com.example.hackernews.data.news.NewsDTO
import com.example.hackernews.database.entities.CommentEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {

    @GET("/v0/{type}.json")
    fun getStoriesIds(@Path("type") type: String): Call<List<Int>>

    @GET("/v0/item/{id}.json")
    fun getStory(@Path("id") id: Int): Call<NewsDTO>

    @GET("/v0/item/{id}.json")
    fun getComment(@Path("id") id: Int): Call<CommentEntity>
}
