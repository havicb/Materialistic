package com.example.hackernews.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.database.entities.Comment
import com.example.hackernews.databinding.CommentRowBinding

class CommentsAdapter(
    private val comments: ArrayList<Comment> = arrayListOf()
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
        holder.bind(comments[position])
    }

    override fun getItemId(position: Int): Long = comments[position].id.toLong()

    override fun getItemCount(): Int {
        return comments.size
    }

    fun addComments(data: List<Comment>) {
        comments.clear()
        comments.addAll(data)
        notifyDataSetChanged()
    }
}
