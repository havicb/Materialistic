package com.example.hackernews.data.helpers

import android.content.Context
import android.icu.text.RelativeDateTimeFormatter
import android.os.Build
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.Instant

class Helper {
    companion object {

        @RequiresApi(Build.VERSION_CODES.N)

        fun countList(list: List<Int>?): Int {
            if (list == null)
                return 0
            return list.size
        }

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

        fun toHours(unixTime: String): Long {
            var unixNow: Long = 0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                unixNow = Instant.now().epochSecond
            }
            val publishedAgo = unixNow - unixTime.toInt()
            return (publishedAgo / 3600).toLong()
        }

        fun printErrorCodes(context: Context, responseCode: Int) {
            when (responseCode) {
                400 -> {
                    Toast.makeText(context, "Bad request", Toast.LENGTH_SHORT).show()
                }
                401 -> {
                    Toast.makeText(context, "Unauthorized", Toast.LENGTH_SHORT).show()
                }
                404 -> {
                    Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fun trimEditText(field: EditText): String {
            return field.text.toString().trim() { it <= ' ' }
        }

    }
}