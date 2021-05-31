package com.example.hackernews.presentation.view.adapters.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.databinding.NewsRowBinding
import com.example.hackernews.domain.entities.News
import com.example.hackernews.presentation.view.news.NewsView
import com.example.hackernews.presentation.view.news.toView

typealias SelectedNewsListener = (News) -> Unit

class NewsAdapter(
    val news: ArrayList<News> = arrayListOf(),
    private val listener: SelectedNewsListener,
    private val itemMenuListener: (News, View) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val newsCopy = arrayListOf<News>()

    init {
        setHasStableIds(true)
        newsCopy.addAll(news)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position], listener, itemMenuListener, position)
    }

    override fun getItemCount(): Int = news.size

    override fun getItemId(position: Int): Long = news[position].id.toLong()

    fun addNews(data: List<News>) {
        news.clear()
        news.addAll(data)
        newsCopy.clear()
        newsCopy.addAll(data)
        notifyDataSetChanged()
    }

    fun filter(text: String) {
        news.clear()
        if (text.isEmpty()) {
            news.addAll(newsCopy)
        } else {
            newsCopy.forEach { currentNews ->
                if (currentNews.title.contains(text))
                    news.add(currentNews)
            }
        }
        notifyDataSetChanged()
    }

    class NewsViewHolder(
        private val binding: NewsRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            news: News,
            listener: SelectedNewsListener,
            itemMenuListener: (News, View) -> Unit,
            position: Int
        ) {
            // bad
            val nView = news.toView()
            binding.newsId.text = (position + 1).toString()
            binding.newsTitle.text = nView.title
            binding.newsScore.text = nView.score.plus("p")
            binding.newsUrl.text = nView.url
            binding.timePublished.text = nView.time
            binding.newsPublisher.text = nView.by
            binding.tvNumComments.text = nView.commentsNum
            binding.root.setOnClickListener {
                listener(news)
            }
            binding.imageViewOptions.setOnClickListener { itemClicked ->
                itemMenuListener(news, itemClicked)
            }
        }
    }
}
