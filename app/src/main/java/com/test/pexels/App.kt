package com.test.pexels

import android.app.Application

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Dagger.buildComponents(applicationContext)
    }
}