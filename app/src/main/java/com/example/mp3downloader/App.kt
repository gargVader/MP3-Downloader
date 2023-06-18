package com.example.mp3downloader

import android.app.Application
import android.content.Context
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import dagger.hilt.android.HiltAndroidApp

lateinit var app: App

val py by lazy {
    Python.start(AndroidPlatform(app))
    Python.getInstance()
}

fun mainMod() = py.getModule("main")

@HiltAndroidApp
class App : Application(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        app = this
    }
}