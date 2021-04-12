package com.example.hackernews.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hackernews.common.constants.Constants
import com.example.hackernews.databinding.FragmentArticleBinding
import com.example.hackernews.viewmodel.article.ArticleFragmentViewModel
import com.example.hackernews.viewmodel.article.ArticleFragmentViewModelFactory

class ArticleFragment : Fragment() {

    private lateinit var factory: ArticleFragmentViewModelFactory
    private lateinit var viewModel: ArticleFragmentViewModel
    private lateinit var binding: FragmentArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            it?.getString(Constants.SELECTED_URL).let{
                factory = ArticleFragmentViewModelFactory(it!!)
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(ArticleFragmentViewModel::class.java)
        binding.articleWebView.webViewClient = viewModel.client.value!!
        binding.articleWebView.loadUrl(viewModel.url.value!!)

    }

    companion object {
        @JvmStatic
        fun newInstance(url: String) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.SELECTED_URL, url)
                }
            }
    }
}