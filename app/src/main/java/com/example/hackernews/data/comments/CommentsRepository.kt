package com.example.hackernews.data.comments

import android.text.Html
import android.util.Log
import com.example.hackernews.data.service.NewsService
import com.example.hackernews.database.entities.CommentEntity
import com.example.hackernews.domain.entities.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentsRepository(private val newsApi: NewsService) {

    fun fetchComments(selectedNews: News, onFetch: (CommentEntity?) -> Unit) {
        selectedNews.kids?.forEach { commentId ->
            newsApi.getComment(commentId).enqueue(object : Callback<CommentEntity> {
                override fun onResponse(
                    call: Call<CommentEntity>,
                    response: Response<CommentEntity>
                ) {
                    val comment = response.body()
                    if (comment!!.text != null) {
                        comment.text = Html.fromHtml(comment.text).toString()
                        onFetch(comment)
                    }
                }

                override fun onFailure(call: Call<CommentEntity>, t: Throwable) {
                    Log.d("CALLING", "FAILED TO FETCH")
                }
            })
        }
    }
}
