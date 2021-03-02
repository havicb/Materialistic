package com.example.hackernews.services

import com.example.hackernews.models.TopStories
import com.example.hackernews.news.NewsDataType
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {

    @GET("/v0/{type}.json")
    fun getStoriesIds(@Path("type") type: String) : Call<List<Int>>
}