package com.example.hackernews.data.api

import android.content.Context
import android.util.Log
import com.example.hackernews.common.callbacks.LoadCommentCallback
import com.example.hackernews.common.callbacks.LoadDataCallback
import com.example.hackernews.common.constants.Api
import com.example.hackernews.common.helpers.Helper
import com.example.hackernews.model.network.Comment
import com.example.hackernews.model.network.NewsM
import com.example.hackernews.common.enums.NewsDataType
import com.example.hackernews.data.service.NewsService
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

class CallApi(val context: Context) : Serializable {

    private val retrofit: NewsService by lazy {
        Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsService::class.java)
    }
    private var job: Job? = null

    fun loadComments(commentsId: List<Int>, loadCommentCallback: LoadCommentCallback) {
        commentsId.indices.forEach {
            loadComment(commentsId[it], loadCommentCallback)
        }
    }

    private fun loadComment(commentId: Int, loadCommentCallback: LoadCommentCallback) {
        val service = retrofit.loadSingleComment(commentId).enqueue(object : Callback<Comment> {
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                loadCommentCallback.onCommentLoaded(response.body()!!)
            }

            override fun onFailure(call: Call<Comment>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    /* before this function i was like how it is possible to load 200 news, when i have only 10 in my database..
     so i started debuging.. and debuging..
     2 days later I realize even when i click on saved story, this job continues to work on different thread
     and function loads me news from database, but it continue to load remaining news from TOP_STORIES
     I just tried with this function, and guess what? It works :D*/
    fun stopLoadingNews() {
        job!!.cancel()
    }

    fun loadSingleNews(newsId: Int, callback: LoadDataCallback) {
        val service = retrofit.loadSingleStory(newsId).enqueue(object : Callback<NewsM?> {
            override fun onResponse(call: Call<NewsM?>, response: Response<NewsM?>) {
                Log.d("Loading news id -> ", newsId.toString())
                callback.onSuccess(response.body()!!)
            }

            override fun onFailure(call: Call<NewsM?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}

