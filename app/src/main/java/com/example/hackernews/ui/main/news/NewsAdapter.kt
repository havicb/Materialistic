package com.example.hackernews.ui.main.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.R
import com.example.hackernews.data.helpers.Helper
import com.example.hackernews.data.model.NewsM

class NewsAdapter(
    var allNews: ArrayList<NewsM> = ArrayList<NewsM>(), val listener: (NewsM) -> Unit,
    var savedNews: ArrayList<NewsM> = ArrayList<NewsM>()
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvID: TextView = view.findViewById(R.id.news_id)
        val tvTitle: TextView = view.findViewById(R.id.news_title)
        val tvNewsScore: TextView = view.findViewById(R.id.news_score)
        val tvNewsUrl: TextView = view.findViewById(R.id.news_url)
        val tvNewsTimePublished: TextView = view.findViewById(R.id.time_published)
        val tvNewsPublisher: TextView = view.findViewById(R.id.news_publisher)
        val tvCommentsNum: TextView = view.findViewById(R.id.tv_num_comments)

        init {
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
        holder.tvCommentsNum.text = Helper.countList(allNews[position].kids).toString()
    }

    override fun getItemCount(): Int {
        return allNews.size
    }

    fun addNews(myList: ArrayList<NewsM>) {
        clear()
        allNews = myList
        notifyDataSetChanged()
    }

    fun getNews(currentPosition: Int): NewsM? {
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