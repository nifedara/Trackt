package com.example.trackt.data.di

import android.content.Context
import com.example.trackt.data.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideOnboardingManager(@ApplicationContext context: Context)
    = DataStoreRepository(context = context)
}