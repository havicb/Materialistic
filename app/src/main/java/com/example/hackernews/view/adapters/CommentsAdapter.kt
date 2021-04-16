package com.example.hackernews.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.databinding.CommentRowBinding
import com.example.hackernews.model.network.Comment

class CommentsAdapter(
    var allComments: ArrayList<Comment> = ArrayList()
) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    init {
        setHasStableIds(true)
    }

    inner class CommentsViewHolder(private val binding: CommentRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.commentText.text = comment.text
            binding.commentPublishedAt.text = comment.time.toString()
            binding.commentPublisher.text = comment.by
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = CommentRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(allComments[position])
    }

    override fun getItemId(position: Int): Long = allComments[position].id.toLong()

    override fun getItemCount(): Int {
        return allComments.size
    }

    fun addComments(comments: ArrayList<Comment>) {
        allComments.clear()
        allComments.addAll(comments)
        notifyDataSetChanged()
    }
}