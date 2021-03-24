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

private const val ARG_PARAM1 = "URL"

class ArticleFragment : Fragment(), Navigable {

    private var param1: String? = null
    private lateinit var binding: FragmentArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ARGUMENTS", arguments.toString())
        arguments?.let {
            Log.d("ARG", it.getString(ARG_PARAM1).toString())
            param1 = it.getString(ARG_PARAM1)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(context, "$param1", Toast.LENGTH_SHORT).show()
        binding = FragmentArticleBinding.inflate(layoutInflater)
        binding.articleWebView.webViewClient = WebViewClient()
        binding.articleWebView.loadUrl("github.com")
        val webSettings = binding.articleWebView.settings
        webSettings.javaScriptEnabled = true
        return binding.root
    }

    override fun navigateToAnotherFragment() {
        view?.post {
            findNavController().navigate(R.id.action_articleFragment_to_commentFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}