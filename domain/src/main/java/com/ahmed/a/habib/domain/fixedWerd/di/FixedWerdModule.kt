package com.ahmed.a.habib.domain.fixedWerd.di


import com.ahmed.a.habib.data.fixedWerd.local.FixedWerdDao
import com.ahmed.a.habib.data.fixedWerd.repo.FixedWerdRepo
import com.ahmed.a.habib.domain.fixedWerd.repoImpl.FixedWerdRepoImpl
import com.ahmed.a.habib.domain.fixedWerd.useCases.FixedWerdUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FixedWerdModule {

    @Provides
    @Singleton
    fun provideFixedWerdRepo(fixedWerdDao: FixedWerdDao): FixedWerdRepo {
        return FixedWerdRepoImpl(fixedWerdDao)
    }

    @Provides
    @Singleton
    fun provideFixedWerdUseCase(fixedWerdRepo: FixedWerdRepo): FixedWerdUseCases {
        return FixedWerdUseCases(fixedWerdRepo)
    }
}