package com.example.hackernews.services

import com.example.hackernews.models.Comment
import com.example.hackernews.models.NewsM
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {
    @GET("/v0/{type}.json")
    suspend fun getStoriesIds(@Path("type") type: String): Response<List<Int>>

    @GET("/v0/item/{id}.json")
    suspend fun getSingleStory(@Path("id") id: Int): Response<NewsM>

    @GET("/v0/item/{id}.json")
    suspend fun getComments(@Path("id") id: Int): Call<Comment>
}