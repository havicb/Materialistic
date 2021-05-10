package com.example.hackernews.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackernews.databinding.FragmentCommentBinding
import com.example.hackernews.factories.CommentViewModelFactory
import com.example.hackernews.model.entities.News
import com.example.hackernews.view.activities.NewsActivity
import com.example.hackernews.view.adapters.CommentsAdapter
import com.example.hackernews.view.common.BaseFragment
import com.example.hackernews.viewmodel.CommentsViewModel

class CommentFragment(private val selectedNews: News) : BaseFragment() {

    private lateinit var binding: FragmentCommentBinding
    private val commentAdapter: CommentsAdapter by lazy {
        CommentsAdapter()
    }

    private val viewModel: CommentsViewModel by viewModels {
        CommentViewModelFactory((activity as NewsActivity).commentsRepository, selectedNews)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindRecycler()
        viewModel.comments.observe(viewLifecycleOwner) { comments ->
            commentAdapter.addComments(comments)
        }
        viewModel.commentsAreWaitingToBeLoaded.observe(viewLifecycleOwner) { areWaiting ->
            if (!areWaiting) {
                binding.loadingCommentProgress.visibility = View.GONE
                return@observe
            }
        }
    }

    private fun bindRecycler() {
        binding.newsComments.layoutManager = LinearLayoutManager(context)
        binding.newsComments.adapter = commentAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(news: News) = CommentFragment(news)
    }
}