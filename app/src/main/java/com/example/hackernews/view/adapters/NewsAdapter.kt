package com.example.hackernews.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.common.helpers.Helper
import com.example.hackernews.databinding.NewsRowBinding
import com.example.hackernews.model.entities.News

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

    class NewsViewHolder(
        private val binding: NewsRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            news: News,
            listener: SelectedNewsListener,
            itemMenuListener: (News, View) -> Unit,
            position: Int
        ) {
            binding.newsId.text = (position + 1).toString()
            binding.newsTitle.text = news.title
            binding.newsScore.text = news.score.toString().plus("p")
            binding.newsUrl.text = Helper.getMainUrl(news.url)
            binding.timePublished.text = Helper.formatDate(news.time)
            binding.newsPublisher.text = news.by
            binding.tvNumComments.text = (news.kids?.size ?: "0").toString()
            binding.root.setOnClickListener {
                listener(news)
            }
            binding.imageViewOptions.setOnClickListener { itemClicked ->
                itemMenuListener(news, itemClicked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentNews = news[position]
        holder.bind(currentNews, listener, itemMenuListener, position)
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
}