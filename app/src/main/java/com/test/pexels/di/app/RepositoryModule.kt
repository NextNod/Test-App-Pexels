package com.test.pexels.di.app

import android.content.Context
import com.test.pexels.data.repo.pexels.IPexelsRepository
import com.test.pexels.data.repo.pexels.PexelsRepository
import com.test.pexels.data.repo.prefs.IPreferencesRepository
import com.test.pexels.data.repo.prefs.PreferencesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(private val context: Context) {

    @Provides
    @Singleton
    fun providePreferencesRepository(): IPreferencesRepository = PreferencesRepository(context)

    @Provides
    @Singleton
    fun providePexelsRepository() : IPexelsRepository = PexelsRepository(context)
}