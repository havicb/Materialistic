package com.example.hackernews.model.repository

import android.text.Html
import android.util.Log
import com.example.hackernews.data.service.NewsService
import com.example.hackernews.model.entities.Comment
import com.example.hackernews.model.entities.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentsRepository(private val newsApi: NewsService) {

    fun fetchComments(selectedNews: News, onFetch: (Comment?) -> Unit) {
        selectedNews.kids?.forEach { commentId ->
            newsApi.getComment(commentId).enqueue(object : Callback<Comment> {
                override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                    val comment = response.body()
                    if(comment!!.text != null) {
                        comment.text = Html.fromHtml(comment.text).toString()
                        onFetch(comment)
                    }
                }
                override fun onFailure(call: Call<Comment>, t: Throwable) {
                    Log.d("CALLING", "FAILED TO FETCH")
                }
            })
        }
    }
}