package com.example.hackernews.presentation.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hackernews.databinding.FragmentArticleBinding
import com.example.hackernews.di.factories.ArticleViewModelFactory
import com.example.hackernews.di.factories.provideArticleViewModelFactory
import com.example.hackernews.presentation.view.common.BaseFragment
import com.example.hackernews.viewmodel.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleFragment(private val url: String) : BaseFragment() {

    private lateinit var binding: FragmentArticleBinding

    // injecting VM
    @Inject lateinit var articleViewModelFactory: ArticleViewModelFactory
    private val viewModel: ArticleViewModel by viewModels {
        provideArticleViewModelFactory(
            articleViewModelFactory,
            url
        )
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
        viewModel.client.observe(
            viewLifecycleOwner,
            { webViewClient ->
                binding.articleWebView.webViewClient = webViewClient
            }
        )
        viewModel.url.observe(
            viewLifecycleOwner,
            { url ->
                binding.articleWebView.loadUrl(url)
            }
        )
        binding.articleWebView.settings.javaScriptEnabled = true
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String) = ArticleFragment(url)
    }
}
