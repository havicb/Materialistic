package com.example.hackernews.data.service

import com.example.hackernews.model.network.Comment
import com.example.hackernews.model.network.NewsM
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {

    @GET("/v0/{type}.json")
    fun getStoriesIds(@Path("type") type: String): Call<List<Int>>

    @GET("/v0/item/{id}.json")
    fun getStory(@Path("id") id: Int): Call<NewsM>

    @GET("/v0/item/{id}.json")
    fun loadSingleComment(@Path("id") id: Int): Call<Comment>

    @GET("/v0/item/{id}.json")
    fun loadSingleStory(@Path("id") id: Int): Call<NewsM>
}