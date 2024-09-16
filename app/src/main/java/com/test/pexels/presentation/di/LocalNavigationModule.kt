package com.test.pexels.presentation.di

import com.test.pexels.presentation.core.navigation.LocalCiceroneHolder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalNavigationModule {

    @Provides
    @Singleton
    fun provideLocalNavigationHolder(): LocalCiceroneHolder {
        return LocalCiceroneHolder()
    }

}
