package com.example.hackernews.news

import android.annotation.SuppressLint
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
        fun newInstance() : NewsCommentFragment {
            return NewsCommentFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arg = arguments?.getSerializable(Constants.SELECTED_NEWS)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_comments_tab_layout, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Toast.makeText(context, "CALLED ON VIEW CREATED", Toast.LENGTH_SHORT).show()
        commentRecyclerView = view.findViewById(R.id.news_comments)
        commentRecyclerView.layoutManager = LinearLayoutManager(context)
        loadComments()
        commentRecyclerView.adapter = commentAdapter
        if(commentAdapter.itemCount > 0) {
            commentRecyclerView.visibility = View.VISIBLE
        }else {
            val commentText = view.findViewById<TextView>(R.id.tv_comments)
            commentText.visibility = View.VISIBLE
            commentText.text = "No comments for particular news"
        }
    }

    fun loadComments() {
        var commentList = ArrayList<Comment>()
        for(i in 0..5) {
            commentList.add(Comment("someName", 1231321, null, 1231312, "Some dummy comment",
                    1231293, "Comment"))
        }
        commentAdapter.addComments(commentList)
        Log.d("Adapter size -> ", commentAdapter.itemCount.toString())
    }



    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}