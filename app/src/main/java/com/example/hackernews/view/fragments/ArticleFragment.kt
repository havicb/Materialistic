package com.example.hackernews.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hackernews.databinding.FragmentArticleBinding
import com.example.hackernews.factories.ArticleViewModelFactory
import com.example.hackernews.viewmodel.ArticleViewModel

class ArticleFragment(private val url: String) : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private val viewModel: ArticleViewModel by viewModels {
        ArticleViewModelFactory(url)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUrl()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadUrl() {
        viewModel.client.observe(viewLifecycleOwner, {webViewClient ->
            binding.articleWebView.webViewClient = webViewClient
        })
        viewModel.url.observe(viewLifecycleOwner, {url ->
            binding.articleWebView.loadUrl(url)
        })
        binding.articleWebView.settings.javaScriptEnabled = true
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String) = ArticleFragment(url)
    }
}
