package com.app

import android.app.Application

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        APPLICATION_INSTANCE = this
    }

    companion object {
        lateinit var APPLICATION_INSTANCE: MyApp
    }
}