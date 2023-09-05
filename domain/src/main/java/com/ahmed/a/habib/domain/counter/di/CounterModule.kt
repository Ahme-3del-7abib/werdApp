package com.ahmed.a.habib.domain.counter.di

import com.ahmed.a.habib.data.counter.local.CounterDao
import com.ahmed.a.habib.data.counter.repo.CounterRepo
import com.ahmed.a.habib.domain.counter.repoImpl.CounterRepoImpl
import com.ahmed.a.habib.domain.counter.useCases.CounterUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CounterModule {

    @Provides
    @Singleton
    fun provideCounterRepo(customWerdDao: CounterDao): CounterRepo {
        return CounterRepoImpl(customWerdDao)
    }

    @Provides
    @Singleton
    fun provideCounterUseCase(repo: CounterRepo): CounterUseCases {
        return CounterUseCases(repo)
    }
}