package com.example.hackernews.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.R
import com.example.hackernews.helpers.Helper
import com.example.hackernews.models.NewsM

class NewsAdapter(var allNews: ArrayList<NewsM> = ArrayList<NewsM>(), val listener: (NewsM) -> Unit,
                var savedNews: ArrayList<NewsM> = ArrayList<NewsM>()) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvID: TextView
        val tvTitle: TextView
        val tvNewsScore: TextView
        val tvNewsUrl: TextView
        val tvNewsTimePublished: TextView
        val tvNewsPublisher: TextView

        init {
            tvID = view.findViewById(R.id.news_id)
            tvTitle = view.findViewById(R.id.news_title)
            tvNewsPublisher = view.findViewById(R.id.news_publisher)
            tvNewsScore = view.findViewById(R.id.news_score)
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
        holder.tvID.text = allNews[position].idToShow.toString()
        holder.tvTitle.text = allNews[position].title
        holder.tvNewsScore.text = allNews[position].score.toString()
        holder.tvNewsUrl.text = Helper.getMainUrl(allNews[position].url)
        holder.tvNewsTimePublished.text = Helper.humanReadableDate(allNews[position].time)
        holder.tvNewsPublisher.text = allNews[position].by
    }

    override fun getItemCount(): Int {
        return allNews.size
    }

    fun addNews(myList: ArrayList<NewsM>) {
        clear()
        allNews = myList
        notifyDataSetChanged()
    }

    fun getNews(currentPosition: Int) : NewsM? {
        return allNews[currentPosition]
    }

    fun addNews(news: NewsM) {
        news.idToShow = allNews.size + 1
        allNews.add(news)
        notifyDataSetChanged()
    }

    fun saveNews(news: NewsM) {
        savedNews.add(news)
        notifyDataSetChanged()
    }

    fun deleteNews(news: NewsM) {
        savedNews.remove(news)
        notifyDataSetChanged()
    }

    fun clear() {
        allNews.clear()
        notifyDataSetChanged()
    }
}