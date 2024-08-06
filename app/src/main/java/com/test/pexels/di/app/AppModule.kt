package com.test.pexels.di.app

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    private var cicerone: Cicerone<Router> = Cicerone.create(Router())

    private val gson = Gson()

    @Provides
    @Singleton
    internal fun provideRouter(): Router {
        return cicerone.router
    }

    @Provides
    @Singleton
    internal fun provideNavigatorHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return gson
    }
}