package com.example.hackernews.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.hackernews.constants.Api
import com.example.hackernews.helpers.Helper
import com.example.hackernews.models.NewsM
import com.example.hackernews.news.NewsAdapter
import com.example.hackernews.news.NewsDataType
import com.example.hackernews.services.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CallApi(val context: Context) {

    lateinit var retrofit: Retrofit

    fun getStories(newsDataType: NewsDataType, newsAdapter: NewsAdapter) {
        retrofit = Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(NewsService::class.java)
        val newsType = NewsDataType.convertValue(newsDataType)
        val call = service.getStoriesIds(newsType)

        val storiesIds = ArrayList<Int>()

        call.enqueue(object : Callback<List<Int>> {
            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                if (response.isSuccessful) {
                    for (i in 0..response.body()!!.size - 1) {
                        storiesIds.add(response.body()!![i])
                        loadNews(response.body()!![i], newsAdapter)
                    }
                } else {
                    // todo
                }
            }
            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                Toast.makeText(context, "Call failed", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loadNews(id: Int, newsAdapter: NewsAdapter) {
        retrofit = Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(NewsService::class.java)
        val call = service.getSingleStory(id)

        call.enqueue(object : Callback<NewsM> {
            var instance = 0
            override fun onResponse(call: Call<NewsM>, response: Response<NewsM>) {
                if (response.isSuccessful) {
                    val trimedUrl = Helper.trimUrl(response.body()!!.url)
                    response.body()?.time = Helper.toHours(response.body()?.time.toString())
                    newsAdapter.addNews(response.body()!!)
                } else {
                    when (response.code()) {
                        400 -> {
                            Toast.makeText(context, "Bad request", Toast.LENGTH_SHORT).show()
                        }
                        401 -> {
                            Toast.makeText(context, "Unauthorized", Toast.LENGTH_SHORT).show()
                        }
                        404 -> {
                            Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<NewsM>, t: Throwable) {
                Log.d("Failed to load news", "${t.message}")
            }
        })
    }
}