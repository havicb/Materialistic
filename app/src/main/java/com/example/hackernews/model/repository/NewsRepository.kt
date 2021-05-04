package com.example.hackernews.model.repository

import android.util.Log
import com.example.hackernews.common.enums.NewsDataType
import com.example.hackernews.data.service.NewsService
import com.example.hackernews.model.database.dao.NewsDao
import com.example.hackernews.model.entities.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService

/**
 * Points for using repository. But few things to keep in mind with repositories:
 *  - There should be multiple repositories per app and each repository is tasked with managing one type of data.
 *    For example we can have UserRepository that only does user related actions (getUser, updateUser, saveUsers, createUser etc.),
 *    or we can have StoryRepository that does story related tasks etc.
 *  - Repository is pattern found on data layer and is responsible for managing multiple data sources (API, database, cache etc.).
 *    Repository should hold instance of api client and dao and call each one when necessary.
 *    ViewModel should not care from where data is coming.
 *  - Repositories should be stateless, this means they are not holding any data.
 *    Therefore we can have single instance per app (singleton).
 *
 * Let us now create StoryRepository as an example.
 */
typealias GetNewsCallback = (News?, ApiError?) -> Unit

class NewsRepository(
    private val newsApi: NewsService,
    private val executors: ExecutorService,
    private val newsDao: NewsDao
) {

    /**
     * We are going to send data back to ViewModel with the help of callbacks.
     * We have defined typealias for function type that has two nullable parameters (NewsM and ApiError).
     * In case we have an error NewsM will be null and in case we have a successful call ApiError is null.
     * Typealias is like nickname of a type.
     */
    fun getNews(newsDataType: NewsDataType, callback: GetNewsCallback) {
        newsApi.getStoriesIds(newsDataType.rawValue).enqueue(object : Callback<List<Int>> {
            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                if (response.isSuccessful)
                    getStories(response.body()!!, callback, newsDataType)
                else
                    callback(null, ApiError(response.errorBody().toString()))
            }

            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                callback(null, ApiError(t.toString()))
            }
        })
    }

    private fun getStories(storiedIds: List<Int>, callback: GetNewsCallback, newsDataType: NewsDataType) {
        storiedIds.forEach { storyId ->
            newsApi.getStory(storyId).enqueue(object : Callback<News> {
                override fun onResponse(call: Call<News>, response: Response<News>) {
                    if (response.isSuccessful) {
                        executors.execute {
                            response.body()!!.newsType = newsDataType.rawValue
                            newsDao.save(response.body()!!)
                        }
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
        executors.execute {
            onLoad(newsDao.loadStories(type))
        }
    }
}

/**
 * For simplicity here we only define error that has error body as string property.
 */
data class ApiError(val error: String)
