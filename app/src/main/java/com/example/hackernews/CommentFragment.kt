package com.example.hackernews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.comments.CommentsAdapter
import com.example.hackernews.data.CallApi
import com.example.hackernews.databinding.FragmentCommentBinding
import com.example.hackernews.interfaces.Navigable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommentFragment : Fragment(), Navigable {

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
        savedInstanceState: Bundle?): View? {
        binding = FragmentCommentBinding.inflate(layoutInflater)
        binding.newsComments.layoutManager = LinearLayoutManager(context)
        loadParentComments(binding.newsComments)
        binding.newsComments.adapter = commentAdapter
        binding.newsComments.visibility = View.VISIBLE
        return binding.root
    }


    override fun navigateToAnotherFragment() {
        view?.post {
            findNavController().navigate(R.id.action_commentFragment_to_articleFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CommentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            CommentFragment().apply {

            }
    }

    fun loadParentComments(view: RecyclerView?) {
        (activity as NewsActivity).getParentComments(view)
    }
}