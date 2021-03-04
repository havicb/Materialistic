package com.example.hackernews.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.hackernews.comments.CommentsAdapter
import com.example.hackernews.constants.Api
import com.example.hackernews.helpers.Helper
import com.example.hackernews.models.Comment
import com.example.hackernews.models.NewsM
import com.example.hackernews.news.NewsAdapter
import com.example.hackernews.news.NewsDataType
import com.example.hackernews.services.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

class CallApi(val context: Context) : Serializable{

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private val newsApi by lazy {
        retrofit.create(NewsService::class.java)
    }

    fun getStories(newsDataType: NewsDataType, newsAdapter: NewsAdapter) {
        val storiesIds = ArrayList<Int>()
        val call = newsApi.getStoriesIds(newsDataType.rawValue).enqueue(object : Callback<List<Int>> {
            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                if (response.isSuccessful) {
                    for (i in response.body()!!.indices) {
                        storiesIds.add(response.body()!![i])
                        loadNews(response.body()!![i], newsAdapter)
                    }
                } else {
                    Helper.printErrorCodes(context, response.code())
                }
            }

            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                Toast.makeText(context, "Call failed", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loadNews(id: Int, newsAdapter: NewsAdapter) {
        newsApi.getSingleStory(id).enqueue(object : Callback<NewsM> {
            override fun onResponse(call: Call<NewsM>, response: Response<NewsM>) {
                if (response.isSuccessful) {
                    response.body()?.time = Helper.toHours(response.body()?.time.toString())
                    newsAdapter.addNews(response.body()!!)
                } else {
                    Helper.printErrorCodes(context, response.code())
                }
            }

            override fun onFailure(call: Call<NewsM>, t: Throwable) {
                Log.d("Failed to load news", "${t.message}")
            }
        })
    }

    fun loadComments(selectedNews: NewsM, commentsAdapter: CommentsAdapter): Unit {
        val service = retrofit.create(NewsService::class.java)
        Log.d("LOAD COMMENTS", "${selectedNews}")
        if (selectedNews.kids?.isEmpty() == true)
            return
        for (i in selectedNews.kids!!.indices) {
            val call = service.getComments(selectedNews.kids[i])
            call.enqueue(object : Callback<Comment> {
                override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                    if (response.isSuccessful) {
                        commentsAdapter.addComment(response.body()!!)
                        Log.d("ADDED COMMENT -> ", "${response.body()}")
                    } else {
                        Log.d("FAILED TO LOAD", "COMMENT")
                        Helper.printErrorCodes(context, response.code())
                    }
                }

                override fun onFailure(call: Call<Comment>, t: Throwable) {
                    Log.d("Failed to load comments..", "${t.message}")
                }
            })
        }
    }
}