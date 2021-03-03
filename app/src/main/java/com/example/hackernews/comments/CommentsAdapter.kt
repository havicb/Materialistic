package com.example.hackernews.comments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.R
import com.example.hackernews.models.Comment

class CommentsAdapter(var allComments: ArrayList<Comment> = ArrayList()
) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    inner class CommentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var publisher: TextView
        var publishedAt: TextView
        var text: TextView

        init {
            text = view.findViewById(R.id.comment_text)
            publishedAt = view.findViewById(R.id.comment_published_at)
            publisher = view.findViewById(R.id.comment_publisher)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_row, parent, false)
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.publisher.text = allComments[position].by
        holder.publishedAt.text = allComments[position].time.toString()
        holder.text.text = allComments[position].text
    }

    override fun getItemCount(): Int {
        return allComments.size
    }

    fun addComments(comments: ArrayList<Comment>) {
        allComments.addAll(comments)
        notifyDataSetChanged()
    }

    fun addComment(comment: Comment) {
        allComments.add(comment)
        notifyDataSetChanged()
    }
}