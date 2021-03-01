package com.example.hackernews.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.hackernews.constants.Api
import com.example.hackernews.models.TopStories
import com.example.hackernews.news.News
import com.example.hackernews.services.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CallApi(val context: Context) {


    fun getStories() {
       val retrofit = Retrofit.Builder()
           .baseUrl(Api.BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()

        val service = retrofit.create(NewsService::class.java)

        val call = service.getTopStories()

        Log.d("API URL " ,"${call.request().url()}")

        call.enqueue(object : Callback<List<TopStories>> {
            override fun onResponse(
                call: Call<List<TopStories>>,
                response: Response<List<TopStories>>
            ) {
                if(response.isSuccessful) {
                    Toast.makeText(context, "Api call is succesfull", Toast.LENGTH_LONG).show()
                    Log.d("RESPONSE BODY",  "${response.body()}")
                }else {
                    Toast.makeText(context, "Api call is not succesfull", Toast.LENGTH_LONG).show()
                    Log.d("Call is not succesfull" ,"response.message()")
                }
            }
            override fun onFailure(call: Call<List<TopStories>>, t: Throwable) {
                Toast.makeText(context, "Call failed", Toast.LENGTH_LONG).show()
                Log.d("Call failed",  "${t.localizedMessage}")
            }
        })
    }

}