package com.example.hackernews.data

import android.content.Context
import android.util.Log
import android.util.Log.d
import com.example.hackernews.auth.AuthUser
import com.example.hackernews.callbacks.LoadDataCallback
import com.example.hackernews.constants.Api
import com.example.hackernews.helpers.Helper
import com.example.hackernews.models.NewsM
import com.example.hackernews.news.NewsDataType
import com.example.hackernews.services.NewsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable
import java.lang.Exception

class CallApi(val context: Context) : Serializable{

    private val retrofit: NewsService by lazy {
        Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NewsService::class.java)
    }

    // this code works, but it is pretty slow, 10 news per sec
    // try to find causer
    fun getStories(newsDataType: NewsDataType, loadData: LoadDataCallback) {
        val allNews = ArrayList<NewsM>()
        CoroutineScope(Dispatchers.IO).launch {
            val storiesIds = retrofit.getStoriesIds(newsDataType.rawValue).body() as ArrayList<Int>
            (storiesIds.indices).forEach {
                val news = loadNews(storiesIds[it])
                allNews.add(news!!)
                withContext(Dispatchers.Main) {
                    if(storiesIds.isNotEmpty() && allNews.isNotEmpty()) {
                        loadData.onSuccess(news)
                    }
                    else
                        loadData.onFailed(throw Exception("Failed to load data"))
                }
            }
        }
    }

     suspend fun loadNews(newsId: Int) : NewsM? {
        return retrofit.getSingleStory(newsId).body().also { newsM ->
            newsM?.time = Helper.toHours(newsM?.time.toString())
        }
    }
    /*
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
*/
}

