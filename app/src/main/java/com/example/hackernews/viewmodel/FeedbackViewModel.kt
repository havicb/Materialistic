package com.example.hackernews.viewmodel

import android.util.Log
import javax.inject.Inject

class FeedbackViewModel @Inject constructor() : BaseViewModel() {

    fun sendFeedback() {
        Log.d("CALLING", "CALLED SEND FEEDBACK")
    }
}
