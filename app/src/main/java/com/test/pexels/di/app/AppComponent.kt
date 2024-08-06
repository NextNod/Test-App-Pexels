package com.test.pexels.di.app

import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.google.gson.Gson
import com.test.pexels.data.repo.pexels.IPexelsRepository
import com.test.pexels.data.repo.prefs.IPreferencesRepository
import com.test.pexels.ui.base.BaseActivity
import com.test.pexels.utils.navigation.LocalCiceroneHolder
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        LocalNavigationModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    fun gson(): Gson

    fun provideNavigatorHolder(): NavigatorHolder
    fun provideLocalNavigationHolder(): LocalCiceroneHolder
    fun provideGlobalRouter(): Router
    fun providePreferencesRepository() : IPreferencesRepository
    fun providePexelsRepository() : IPexelsRepository
    fun inject(activity: BaseActivity)
}