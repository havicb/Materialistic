package com.example.hackernews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.comments.CommentsAdapter
import com.example.hackernews.data.CallApi
import com.example.hackernews.databinding.FragmentCommentBinding

class CommentFragment : Fragment() {

    private lateinit var binding: FragmentCommentBinding

    val commentAdapter: CommentsAdapter by lazy {
        CommentsAdapter()
    }
    private lateinit var apiCall: CallApi


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBinding.inflate(layoutInflater)
        binding.newsComments.layoutManager = LinearLayoutManager(context)
        loadParentComments(binding.newsComments)
        binding.newsComments.adapter = commentAdapter
        binding.newsComments.visibility = View.VISIBLE
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CommentFragment().apply {
            }
    }

    fun loadParentComments(view: RecyclerView?) {
        (activity as NewsActivity).getParentComments(view)
    }
}