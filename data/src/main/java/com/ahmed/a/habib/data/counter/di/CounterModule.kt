package com.ahmed.a.habib.data.counter.di

import com.ahmed.a.habib.data.counter.local.CounterDao
import com.ahmed.a.habib.data.db.WerdDataBase
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
    fun provideCounterDao(werdDataBase: WerdDataBase): CounterDao {
        return werdDataBase.counterDao()
    }
}