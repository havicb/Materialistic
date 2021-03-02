package com.example.hackernews.helpers

import android.os.Build
import java.time.Instant

class Helper {
    companion object {
        fun trimUrl(newsUrl: String?): String {
            return newsUrl ?: " "
        }

        private fun findEndOfMainUrl(newsUrl: String): Int {
            println("Proslijedio $newsUrl")
            for (i in 0..newsUrl.length - 1) {
                if (newsUrl[i] == '/')
                    return i
            }
            return newsUrl.length - 1
        }

        fun toHours(unixTime: String): Long {
            var unixNow: Long = 0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                unixNow = Instant.now().getEpochSecond()
            }
            val publishedAgo = unixNow - unixTime.toInt()
            return (publishedAgo / 3600).toLong()
        }
    }
}