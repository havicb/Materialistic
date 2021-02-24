package com.example.hackernews.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hackernews.R
import com.example.hackernews.databinding.ActivityMainBinding.inflate

class NewsCommentFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_comments_tab_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.tvComment).setText("This is comment tab")
    }


    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}