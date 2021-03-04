package com.example.hackernews.news

import android.content.Context
import android.os.Bundle
import android.telecom.Call
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
import com.example.hackernews.constants.Constants
import com.example.hackernews.data.CallApi
import com.example.hackernews.databinding.ActivityMainBinding.inflate
import com.example.hackernews.models.Comment
import com.example.hackernews.models.NewsM

class NewsCommentFragment : Fragment(), View.OnClickListener {

    private lateinit var commentRecyclerView: RecyclerView
    val commentAdapter: CommentsAdapter by lazy {
        CommentsAdapter()
    }
    private lateinit var apiCall: CallApi


    companion object {
        fun newInstance(selectedNews: NewsM? = null, api: CallApi) : NewsCommentFragment {
            val args = Bundle()
            args.putSerializable(Constants.SELECTED_NEWS, selectedNews)
            args.putSerializable("CALL", api)
            val fragment = NewsCommentFragment()
            fragment.arguments = args
            Log.d("FRAGMENTS -> ", "${fragment.requireArguments().getSerializable(Constants.SELECTED_NEWS)}")
            Log.d("API ->", "${fragment.requireArguments().getSerializable("CALL")}")
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arg = arguments?.getSerializable(Constants.SELECTED_NEWS)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_comments_tab_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("ON VIEW CREATED", "CREATED")
        commentRecyclerView = view.findViewById(R.id.news_comments)
        commentRecyclerView.layoutManager = LinearLayoutManager(context)
        commentRecyclerView.adapter = commentAdapter
    }


    fun loadComments(selectedNews: NewsM?, callApi: CallApi) {
        apiCall = callApi
        Log.d("LOAD COMMENTS -> ", "${apiCall}")
        apiCall?.loadComments(selectedNews!!, commentAdapter)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}