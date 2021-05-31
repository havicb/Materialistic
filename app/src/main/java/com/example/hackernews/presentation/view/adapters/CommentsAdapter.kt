package com.example.hackernews.presentation.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.database.entities.CommentEntity
import com.example.hackernews.databinding.CommentRowBinding

class CommentsAdapter(
    private val commentEntities: ArrayList<CommentEntity> = arrayListOf()
) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    init {
        setHasStableIds(true)
    }

    inner class CommentsViewHolder(private val binding: CommentRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(commentEntity: CommentEntity) {
            binding.commentText.text = commentEntity.text
            binding.commentPublishedAt.text = commentEntity.time.toString()
            binding.commentPublisher.text = commentEntity.by
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = CommentRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(commentEntities[position])
    }

    override fun getItemId(position: Int): Long = commentEntities[position].id.toLong()

    override fun getItemCount(): Int {
        return commentEntities.size
    }

    fun addComments(data: List<CommentEntity>) {
        commentEntities.clear()
        commentEntities.addAll(data)
        notifyDataSetChanged()
    }
}
