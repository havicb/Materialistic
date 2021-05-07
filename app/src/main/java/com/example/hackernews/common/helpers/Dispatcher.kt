package com.example.hackernews.common.helpers

import android.os.Handler
import androidx.annotation.WorkerThread
import com.example.hackernews.common.enums.Dispatchers
import java.util.concurrent.ExecutorService

class Dispatcher(
    private val excecutors: ExecutorService,
    private val mainThread: Handler
) {

    fun launch(dispatchers: Dispatchers, runnable: Runnable) {
        if(dispatchers == Dispatchers.IO) {
            io(runnable)
            return
        }
        main(runnable)
    }
    @WorkerThread
    private fun io(runnable: Runnable) {
        excecutors.execute(runnable)
    }

    private fun main(runnable: Runnable) {
        mainThread.post(runnable)
    }
}