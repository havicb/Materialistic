package com.example.hackernews.services

import com.example.hackernews.models.TopStories
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {

    @GET("/v0/topstories.json")
    fun getTopStories() : Call<List<TopStories>>
}