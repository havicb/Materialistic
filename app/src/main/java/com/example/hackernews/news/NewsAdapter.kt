package com.example.hackernews.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.R
import com.example.hackernews.models.NewsM

class NewsAdapter(var allNews: ArrayList<NewsM> = ArrayList<NewsM>(), val listener: (NewsM) -> Unit) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvID: TextView
        val tvTitle: TextView
        val tvNewsUrl: TextView
        val tvNewsTimePublished: TextView
        val tvNewsPublisher: TextView

        init {
            tvID = view.findViewById(R.id.news_id)
            tvTitle = view.findViewById(R.id.news_title)
            tvNewsPublisher = view.findViewById(R.id.news_publisher)
            tvNewsUrl = view.findViewById(R.id.news_url)
            tvNewsTimePublished = view.findViewById(R.id.time_published)
            view.setOnClickListener {
                listener.invoke(allNews[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_row, parent, false);
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.tvID.text = allNews[position].id.toString()
        holder.tvTitle.text = allNews.get(position).title
        holder.tvNewsUrl.text = allNews[position].url
        holder.tvNewsTimePublished.text = allNews[position].time.toString()
        holder.tvNewsPublisher.text = allNews[position].by
    }

    override fun getItemCount(): Int {
        return allNews.size;
    }

    fun addNews(myList: ArrayList<NewsM>) {
        clear()
        allNews = myList
        notifyDataSetChanged()
    }

    fun addNews(news: NewsM) {
        news.id = allNews.size + 1
        allNews.add(news)
        notifyDataSetChanged()
    }

    fun clear() {
        allNews.clear()
        notifyDataSetChanged()
    }
}