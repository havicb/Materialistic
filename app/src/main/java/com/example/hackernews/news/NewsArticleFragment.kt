package com.example.hackernews.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hackernews.R
import com.example.hackernews.constants.Constants
import com.example.hackernews.models.NewsM

class NewsArticleFragment() : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance(news: NewsM) : NewsArticleFragment{
            val args = Bundle()
            args.putSerializable(Constants.SELECTED_NEWS, news)
            val fragment = NewsArticleFragment()
            fragment.arguments = args
            Log.d("FRAGMENTS ARTICLE-> ", "${fragment.arguments}")
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ARGUMENTS", arguments.toString())
        // todo implement
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_article_tab_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val webView = view.findViewById<WebView>(R.id.web_view)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://facebook.com")
        Log.d("ARGUMENTS", arguments.toString())
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
    }

    override fun onClick(v: View?) {
        //todo
    }
}