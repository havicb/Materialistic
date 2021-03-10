package com.example.hackernews.helpers

import android.content.Context
import android.os.Build
import android.widget.EditText
import android.widget.Toast
import java.time.Instant

class Helper {
    companion object {
        fun toHours(unixTime: String): Long {
            var unixNow: Long = 0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                unixNow = Instant.now().getEpochSecond()
            }
            val publishedAgo = unixNow - unixTime.toInt()
            return (publishedAgo / 3600).toLong()
        }

        fun printErrorCodes(context: Context, responseCode: Int) {
            when(responseCode) {
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

        fun trimEditText(field: EditText) : String {
            return field.text.toString().trim() { it <= ' '}
        }

    }
}