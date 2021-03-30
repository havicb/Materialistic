package com.example.hackernews

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.hackernews.databinding.FragmentArticleBinding
import com.example.hackernews.interfaces.Navigable

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(layoutInflater)
        binding.articleWebView.webViewClient = WebViewClient()
        binding.articleWebView.loadUrl("github.com")

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ArticleFragment().apply {
            }
    }
}