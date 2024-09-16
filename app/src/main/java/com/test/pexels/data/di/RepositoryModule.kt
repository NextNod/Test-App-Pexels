package com.test.pexels.data.di

import android.content.Context
import com.test.pexels.domain.repo.IPexelsRepository
import com.test.pexels.data.repo.pexels.PexelsRepository
import com.test.pexels.domain.repo.IPreferencesRepository
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