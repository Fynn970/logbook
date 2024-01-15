package com.fynn.logbook.di

import com.fynn.logbook.ui.home.HomeViewModel
import com.fynn.logbook.repository.AppRepository
import com.fynn.logbook.repository.IAppRepostory
import com.fynn.logbook.ui.addexperiment.AddExperimentViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideHomeViewModel(repostory: IAppRepostory): HomeViewModel {
        return HomeViewModel(repostory)
    }

    @Provides
    fun provideAppRepository(): IAppRepostory{
        return AppRepository()
    }

    @Provides
    fun provideAddExperimentViewModel(repostory: IAppRepostory): AddExperimentViewModel {
        return AddExperimentViewModel(repostory)
    }

}