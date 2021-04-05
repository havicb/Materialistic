package com.example.hackernews.ui.main.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hackernews.data.model.Comment
import com.example.hackernews.data.model.NewsM
import com.example.hackernews.data.service.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentViewModel() : ViewModel() {

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> get() = _comments

    init {
        getComments()
    }

    fun getComments() {
        val tempList = arrayListOf<Comment>()
        for(i in 0..5) {
            tempList.add(Comment("some", 123123, null, 1231231, "Some dummy comment", 123123, "comment"))
        }
        _comments.value = tempList
    }
    /*
    suspend fun loadSingleComment(commentId: Int) {
        val service = repository.loadSingleComment(commentId).enqueue(object : Callback<Comment> {
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                print("Comment succesfully loaded..")

            }
            override fun onFailure(call: Call<Comment>, t: Throwable) {

            }
        })
    }
     */
}