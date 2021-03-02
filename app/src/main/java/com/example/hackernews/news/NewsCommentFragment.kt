package com.example.hackernews.news

import android.content.Context
import android.os.Bundle
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
import com.example.hackernews.databinding.ActivityMainBinding.inflate
import com.example.hackernews.models.Comment

class NewsCommentFragment() : Fragment(), View.OnClickListener {
    private lateinit var commentRecyclerView: RecyclerView
    private lateinit var commentAdapter: CommentsAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_comments_tab_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        commentRecyclerView = view.findViewById(R.id.news_comments)
        commentRecyclerView.layoutManager = LinearLayoutManager(context)
        commentAdapter = CommentsAdapter()
        val comments = ArrayList<Comment>()
        for(i in 0..5) {
            comments.add(Comment("User", 12321,null, 3, "Some text", 1005254, "comment"))
        }
        commentAdapter.addNews(comments)
        commentRecyclerView.adapter = commentAdapter
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}