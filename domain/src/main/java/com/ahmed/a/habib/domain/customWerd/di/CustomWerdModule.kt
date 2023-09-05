package com.ahmed.a.habib.domain.customWerd.di

import com.ahmed.a.habib.data.customWerd.local.CustomWerdDao
import com.ahmed.a.habib.data.customWerd.repo.CustomWerdRepo
import com.ahmed.a.habib.domain.customWerd.repoImp.CustomWerdRepoImpl
import com.ahmed.a.habib.domain.customWerd.useCases.CustomWerdUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CustomWerdModule {

    @Provides
    @Singleton
    fun provideCustomWerdRepo(customWerdDao: CustomWerdDao): CustomWerdRepo {
        return CustomWerdRepoImpl(customWerdDao)
    }

    @Provides
    @Singleton
    fun provideCustomWerdUseCase(customWerdRepo: CustomWerdRepo): CustomWerdUseCases {
        return CustomWerdUseCases(customWerdRepo)
    }
}