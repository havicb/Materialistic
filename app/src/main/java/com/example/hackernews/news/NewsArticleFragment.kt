package com.example.hackernews.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hackernews.R

class NewsArticleFragment(val url: String) : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_article_tab_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val webView = view.findViewById<WebView>(R.id.web_view)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
    }

    override fun onClick(v: View?) {
        //todo
    }
}