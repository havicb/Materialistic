package com.example.hackernews

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    companion object {
        lateinit var context: Context // I could not make this field val, because it needs do be initialized at place of declaration(you wrote me that way in comment)
                                        // and also it shows me this is a memory leak
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}