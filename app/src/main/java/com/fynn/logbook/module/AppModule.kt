package com.fynn.logbook.module

import com.fynn.logbook.home.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideHomeViewModel():HomeViewModel{
        return HomeViewModel()
    }

}