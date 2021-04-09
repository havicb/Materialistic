package com.example.hackernews.model.network

import java.io.Serializable

data class Comment(
        val by: String,
        val id: Int,
        val kids: List<Int>?,
        val parent: Int,
        val text: String,
        val time: Long,
        val type: String
) : Serializable
