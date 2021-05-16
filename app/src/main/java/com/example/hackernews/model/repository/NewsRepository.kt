package com.example.hackernews.model.repository

import android.util.Log
import com.example.hackernews.common.enums.Dispatchers
import com.example.hackernews.common.enums.NewsDataType
import com.example.hackernews.common.helpers.Dispatcher
import com.example.hackernews.data.service.NewsService
import com.example.hackernews.model.database.dao.NewsDao
import com.example.hackernews.model.entities.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

typealias GetNewsCallback = (News?, ApiError?) -> Unit

class NewsRepository(
    private val newsApi: NewsService,
    private val newsDao: NewsDao,
    private val dispatcher: Dispatcher
) {

    fun getStories(newsDataType: NewsDataType, onFetchStories: (postsIds: List<Int>) -> Unit) {
        newsApi.getStoriesIds(newsDataType.rawValue).enqueue(object : Callback<List<Int>> {
            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                if (response.isSuccessful) {
                    onFetchStories(response.body()!!)
                } else {
                    Log.d("CALLING", "FAILED")
                }
            }
            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                Log.d("CALLING", "FAILED")
            }
        })
    }

    fun getStories(
        storiedIds: List<Int>,
        callback: GetNewsCallback,
    ) {
        storiedIds.forEach { storyId ->
            newsApi.getStory(storyId).enqueue(object : Callback<News> {
                override fun onResponse(call: Call<News>, response: Response<News>) {
                    if (response.isSuccessful) {
                        callback(response.body(), null)
                    } else
                        callback(null, ApiError(response.errorBody().toString()))
                }

                override fun onFailure(call: Call<News>, t: Throwable) {
                    callback(null, ApiError(t.toString()))
                }
            })
        }
    }

    fun loadLocalStories(type: String, onLoad: (List<News>?) -> Unit) {
        dispatcher.launch(Dispatchers.IO) {
            val localStories = newsDao.loadStories(type)
            dispatcher.launch(Dispatchers.MAIN) {
                onLoad(localStories)
            }
        }
    }
}

/**
 * For simplicity here we only define error that has error body as string property.
 */
data class ApiError(val error: String)
