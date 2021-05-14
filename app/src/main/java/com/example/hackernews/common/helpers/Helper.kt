package com.example.hackernews.common.helpers

import java.text.SimpleDateFormat
import java.util.*

class Helper {
    companion object {

        // because i am getting time from api in UNIX, i made this function to convert it into human readable format
        // example 321321 unix -> 2 day ago,
        // 123 -> 4 min ago etc.
        fun formatDate(unix: Long): String {
            val inputDate = Date(unix * 1000L)
            return getDateDifferenceForDisplay(inputDate)
        }

        private fun getDateDifferenceForDisplay(inputdate: Date): String {
            val now = Calendar.getInstance()
            val then = Calendar.getInstance()
            now.time = Date()
            then.time = inputdate

            // Get the represented date in milliseconds
            val nowMs = now.timeInMillis
            val thenMs = then.timeInMillis

            // Calculate difference in milliseconds
            val diff = nowMs - thenMs

            // Calculate difference in seconds
            val diffMinutes = diff / (60 * 1000)
            val diffHours = diff / (60 * 60 * 1000)
            val diffDays = diff / (24 * 60 * 60 * 1000)
            return if (diffMinutes < 60) {
                "$diffMinutes m"
            } else if (diffHours < 24) {
                "$diffHours h"
            } else if (diffDays < 7) {
                "$diffDays d"
            } else {
                val todate = SimpleDateFormat(
                    "MMM dd",
                    Locale.ENGLISH
                )
                todate.format(inputdate)
            }
        }

        /* when i call hacker news rest service, it returns me something like this -> "url" : "http://www.getdropbox.com/u/2/screencast.html"
         in my recycler view i dont need whole url, just main part (www.getdropbox.com).
         so i hope you now have more clean purpose-vision of this function
         */
        fun getMainUrl(fullUrl: String?): String {
            if (fullUrl == null)
                return "news.ycombinator.com"
            var endHttpPartInUrl = if (fullUrl.length < 8) fullUrl.length else 8
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