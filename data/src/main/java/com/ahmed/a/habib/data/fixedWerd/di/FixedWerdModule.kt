package com.ahmed.a.habib.data.fixedWerd.di

import com.ahmed.a.habib.data.db.WerdDataBase
import com.ahmed.a.habib.data.fixedWerd.local.FixedWerdDao
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
    fun provideFixedWerdDao(werdDataBase: WerdDataBase): FixedWerdDao {
        return werdDataBase.fixedWerdDao()
    }
}