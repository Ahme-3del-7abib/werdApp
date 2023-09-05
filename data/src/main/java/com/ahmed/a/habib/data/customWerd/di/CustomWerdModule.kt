package com.ahmed.a.habib.data.customWerd.di

import com.ahmed.a.habib.data.customWerd.local.CustomWerdDao
import com.ahmed.a.habib.data.db.WerdDataBase
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
    fun provideCustomWerdDao(werdDataBase: WerdDataBase): CustomWerdDao {
        return werdDataBase.customWerdDao()
    }
}