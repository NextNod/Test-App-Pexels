package com.test.pexels

import android.content.Context
import com.test.pexels.data.di.AppComponent
import com.test.pexels.data.di.DaggerAppComponent
import com.test.pexels.domain.di.AppModule
import com.test.pexels.data.di.RepositoryModule

object Dagger {

    lateinit var appComponent: AppComponent

    @JvmStatic
    fun buildComponents(context: Context) {
        appComponent = DaggerAppComponent.builder().apply {
            appModule(AppModule())
            repositoryModule(RepositoryModule(context))
        }.build()
    }
}