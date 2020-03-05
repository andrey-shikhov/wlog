package me.shikhov.wlogtest

import android.app.Application

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        System.setProperty("wlog.logLevel", "info")
    }
}