package com.example.hackernews.common.helpers

import android.content.Context
import android.icu.text.RelativeDateTimeFormatter
import android.os.Build
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.Instant

class Helper {
    companion object {

        // because i am getting time from api in UNIX, i made this function to convert it into human readable format
        // example 321321 unix -> 2 day ago,
        // 123 -> 4 min ago etc.
        @RequiresApi(Build.VERSION_CODES.N)
        fun humanReadableDate(hours: Long): String {
            val format = RelativeDateTimeFormatter.getInstance()
            var relativeUnit = RelativeDateTimeFormatter.RelativeUnit.HOURS
            var tempHours = hours
            if (hours > 24) {
                relativeUnit = RelativeDateTimeFormatter.RelativeUnit.DAYS
                tempHours /= 24
            }
            return format.format(
                tempHours.toDouble(),
                RelativeDateTimeFormatter.Direction.LAST,
                relativeUnit
            )
        }

        /* when i call hacker news rest service, it returns me something like this -> "url" : "http://www.getdropbox.com/u/2/screencast.html"
         in my recycler view i dont need whole url, just main part (www.getdropbox.com).
         so i hope you now have more clean purpose-vision of this function
         */
        fun getMainUrl(fullUrl: String?): String {
            if (fullUrl == null)
                return "news.ycombinator.com"
            val trimmedFirstPart = fullUrl.removeRange(0, 8)
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