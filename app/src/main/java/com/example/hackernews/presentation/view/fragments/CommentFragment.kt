package com.example.hackernews.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackernews.databinding.FragmentCommentBinding
import com.example.hackernews.di.factories.CommentViewModelFactory
import com.example.hackernews.di.factories.provideCommentVMFactory
import com.example.hackernews.domain.entities.News
import com.example.hackernews.presentation.view.adapters.CommentsAdapter
import com.example.hackernews.presentation.view.common.BaseFragment
import com.example.hackernews.viewmodel.CommentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommentFragment(private val selectedNews: News) : BaseFragment() {

    private lateinit var binding: FragmentCommentBinding
    private val commentAdapter: CommentsAdapter by lazy {
        CommentsAdapter()
    }

    @Inject lateinit var commentViewModelFactory: CommentViewModelFactory
    private val viewModel: CommentsViewModel by viewModels {
        provideCommentVMFactory(
            commentViewModelFactory, selectedNews
        )
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
        fun newInstance(newsEntity: News) = CommentFragment(newsEntity)
    }
}
