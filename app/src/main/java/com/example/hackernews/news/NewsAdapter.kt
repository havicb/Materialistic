package com.example.hackernews.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.R

class NewsAdapter(val allNews: ArrayList<News>, val listener: (News) -> Unit) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

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
        holder.tvID.text = allNews[position].newsId.toString()
        holder.tvTitle.text = allNews.get(position).newsTitle
        holder.tvNewsUrl.text = allNews[position].newsUrl
        holder.tvNewsTimePublished.text = allNews[position].newsTimePublished
        holder.tvNewsPublisher.text = allNews[position].newsPublisher

    }

    override fun getItemCount(): Int {
        return allNews.size;
    }
}