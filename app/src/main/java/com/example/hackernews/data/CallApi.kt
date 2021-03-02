package com.example.hackernews.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.hackernews.constants.Api
import com.example.hackernews.models.TopStories
import com.example.hackernews.news.News
import com.example.hackernews.news.NewsDataType
import com.example.hackernews.services.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CallApi(val context: Context) {


    fun getStoriesIds(newsDataType: NewsDataType) {
       val retrofit = Retrofit.Builder()
           .baseUrl(Api.BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()

        val service = retrofit.create(NewsService::class.java)
        val newsType = NewsDataType.convertValue(newsDataType)
        val call = service.getStoriesIds(newsType)

        Log.d("API URL -> ", "${call.request().url()}")

        call.enqueue(object : Callback<List<Int>> {
            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                if(response.isSuccessful) {
                    for(i in 0..response.body()!!.size-1) {
                        println("${response.body()!!.get(i)}")
                    }
                }else {
                    // todo
                }
            }
            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                Toast.makeText(context, "Call failed", Toast.LENGTH_LONG).show()
            }
        })
    }

}