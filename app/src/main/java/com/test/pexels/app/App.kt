package com.test.pexels.app

import android.app.Application
import com.test.pexels.di.Dagger

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Dagger.buildComponents(applicationContext)
    }
}