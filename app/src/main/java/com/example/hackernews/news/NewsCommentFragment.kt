package com.example.hackernews.news

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.NewsActivity
import com.example.hackernews.R
import com.example.hackernews.comments.CommentsAdapter
import com.example.hackernews.data.CallApi
import com.example.hackernews.databinding.ActivityMainBinding.inflate
import com.example.hackernews.models.Comment
import com.example.hackernews.models.NewsM

class NewsCommentFragment(val selectedNews: NewsM? = null, context: Context? = null) : Fragment(), View.OnClickListener {

    private lateinit var commentRecyclerView: RecyclerView
    lateinit var commentAdapter: CommentsAdapter
    private val apiCall = context?.let { CallApi(it) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_comments_tab_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        commentRecyclerView = view.findViewById(R.id.news_comments)
        commentRecyclerView.layoutManager = LinearLayoutManager(context)
        commentAdapter = CommentsAdapter()
        loadComments()
        commentRecyclerView.adapter = commentAdapter
    }

    fun loadComments()  {
        apiCall?.loadComments(selectedNews!!, commentAdapter)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}