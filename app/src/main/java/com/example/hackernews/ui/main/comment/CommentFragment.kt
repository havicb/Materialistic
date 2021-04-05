package com.example.hackernews.ui.main.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.ui.main.NewsActivity
import com.example.hackernews.data.api.CallApi
import com.example.hackernews.data.model.Comment
import com.example.hackernews.databinding.FragmentCommentBinding

class CommentFragment : Fragment() {

    private lateinit var binding: FragmentCommentBinding
    private lateinit var apiCall: CallApi
    val commentAdapter: CommentsAdapter by lazy {
        CommentsAdapter()
    }
    private lateinit var viewModel: CommentViewModel
    private lateinit var factory: CommentViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = CommentViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(CommentViewModel::class.java)
        binding.newsComments.layoutManager = LinearLayoutManager(context)
        commentAdapter.addComments(viewModel.comments.value as ArrayList<Comment>)
        binding.newsComments.adapter = commentAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CommentFragment().apply {}
    }

    fun loadParentComments(view: RecyclerView?) {
        (activity as NewsActivity).getParentComments(view)
    }
}