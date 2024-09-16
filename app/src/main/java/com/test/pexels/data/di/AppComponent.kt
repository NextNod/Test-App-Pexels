package com.test.pexels.data.di

import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.google.gson.Gson
import com.test.pexels.domain.di.AppModule
import com.test.pexels.domain.repo.IPexelsRepository
import com.test.pexels.domain.repo.IPreferencesRepository
import com.test.pexels.presentation.core.BaseActivity
import com.test.pexels.presentation.core.navigation.LocalCiceroneHolder
import com.test.pexels.presentation.di.LocalNavigationModule
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