package com.example.hackernews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.hackernews.constants.Constants
import com.example.hackernews.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    private var url: String? = null
    private lateinit var binding: FragmentArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            it?.getString(Constants.SELECTED_URL).let {
                url = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(layoutInflater)
        binding.articleWebView.webViewClient = WebViewClient()
        binding.articleWebView.loadUrl(url!!)
        return binding.root
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