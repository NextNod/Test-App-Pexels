package com.test.pexels.di

import android.content.Context
import com.test.pexels.di.app.AppComponent
import com.test.pexels.di.app.AppModule
import com.test.pexels.di.app.DaggerAppComponent
import com.test.pexels.di.app.RepositoryModule

object Dagger {

    lateinit var appComponent: AppComponent

    @JvmStatic
    fun buildComponents(context: Context) {
        appComponent = buildAppComponent(context)
    }

    private fun buildAppComponent(context: Context) = DaggerAppComponent.builder()
        .appModule(AppModule())
        .repositoryModule(RepositoryModule(context))
        .build()
}