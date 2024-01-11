package com.fynn.logbook.di

import com.fynn.logbook.home.HomeViewModel
import com.fynn.logbook.repository.AppRepository
import com.fynn.logbook.repository.IAppRepostory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideHomeViewModel(repostory: IAppRepostory):HomeViewModel{
        return HomeViewModel(repostory)
    }

    @Provides
    fun provideAppRepository(): IAppRepostory{
        return AppRepository()
    }

}