package com.example.hackernews.data

import android.util.Log
import com.example.hackernews.callbacks.LoadDataCallback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserPostDAO() {
    private val firebaseDB = FirebaseDatabase.getInstance()
    private val databaseRef = firebaseDB.getReference("saved_user_post")


    fun savePost(userToken: String, postID: Int) {
        databaseRef.child(userToken).push().setValue(postID)
    }

    fun loadPosts(
        userToken: String,
        loadDataCallback: LoadDataCallback,
        api: CallApi
    ): ArrayList<Int> {
        val posts = ArrayList<Int>()
        val query = databaseRef.child(userToken).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.hasChildren()) {
                    Log.d("Saved post", "No children")
                    return
                }
                snapshot.children.forEach {
                    // zovi funkciju i proslijedi joj ID
                    val id = it.value as Long
                    println("STORY $id")
                    api.loadSingleNews(id.toInt(), loadDataCallback)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return posts
    }
}