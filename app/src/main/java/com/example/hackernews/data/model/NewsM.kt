package com.example.hackernews.data.model

import java.io.Serializable

data class NewsM(
        val by: String,
        val descendants: Int,
        val id: Int,
        var idToShow: Int,
        val kids: List<Int>?,
        val score: Int,
        var time: Long,
        val title: String,
        val type: String,
        var url: String
) : Serializable
