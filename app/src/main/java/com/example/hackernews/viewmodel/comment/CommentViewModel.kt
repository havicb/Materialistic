package com.example.hackernews.viewmodel.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hackernews.model.network.Comment

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