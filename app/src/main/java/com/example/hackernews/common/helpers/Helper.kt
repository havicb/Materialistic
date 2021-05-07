package com.example.hackernews.common.helpers

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class Helper {
    companion object {

        // because i am getting time from api in UNIX, i made this function to convert it into human readable format
        // example 321321 unix -> 2 day ago,
        // 123 -> 4 min ago etc.
        @RequiresApi(Build.VERSION_CODES.N)
        fun formatDate(unix: Long): String {
            val date = Date(unix * 1000L)
            return SimpleDateFormat("MM-dd HH:mm:ss").format(date)
        }

        /* when i call hacker news rest service, it returns me something like this -> "url" : "http://www.getdropbox.com/u/2/screencast.html"
         in my recycler view i dont need whole url, just main part (www.getdropbox.com).
         so i hope you now have more clean purpose-vision of this function
         */
        fun getMainUrl(fullUrl: String?): String {
            if (fullUrl == null)
                return "news.ycombinator.com"
            var endHttpPartInUrl = if(fullUrl.length < 8) fullUrl.length else 8
            val trimmedFirstPart = fullUrl.removeRange(0, endHttpPartInUrl)
            val mainUrlEndIndex = findMainUrlEnd(trimmedFirstPart)
            var finalUrl = ""
            if (mainUrlEndIndex != -1)
                finalUrl = trimmedFirstPart.removeRange(mainUrlEndIndex, trimmedFirstPart.length)
            return finalUrl
        }

        private fun findMainUrlEnd(fullUrl: String): Int {
            return fullUrl.indexOf('/')
        }

    }
}