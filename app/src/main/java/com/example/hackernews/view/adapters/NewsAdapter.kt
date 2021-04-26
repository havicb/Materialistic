package com.example.hackernews.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.common.helpers.Helper
import com.example.hackernews.databinding.NewsRowBinding
import com.example.hackernews.model.entities.News

typealias SelectedNewsListener = (News) -> Unit

class NewsAdapter(
    val news: ArrayList<News> = arrayListOf(),
    private val listener: SelectedNewsListener
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    init {
        /**
         * setHasStableIds is an optimization hint that you can give to the recycler.
         * You're telling it "when I provide a ViewHolder, its id is unique and will not change."
         * So when you call notifyDataSetChanged with new list, recycler view will look at item's ids
         * and it won't redraw items found with the same ids. This works together with getItemId method.
         */
        setHasStableIds(true)
    }

    class NewsViewHolder(
        private val binding: NewsRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News, listener: SelectedNewsListener) {
            binding.newsId.text = news.idToShow.toString()
            binding.newsTitle.text = news.title
            binding.newsScore.text = news.score.toString()
            binding.newsUrl.text = Helper.getMainUrl(news.url)
            binding.timePublished.text = Helper.humanReadableDate(news.time)
            binding.newsPublisher.text = news.by
            binding.root.setOnClickListener {
                listener(news)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentNews = news[position]
        holder.bind(currentNews, listener)
    }

    override fun getItemCount(): Int = news.size

    override fun getItemId(position: Int): Long = news[position].id.toLong()

    fun addNews(data: List<News>) {
        news.clear()
        news.addAll(data)
        notifyDataSetChanged()
    }
}