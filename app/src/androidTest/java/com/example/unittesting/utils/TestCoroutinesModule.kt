package com.example.unittesting.utils

import com.example.unittesting.remote.DefaultDispatcher
import com.example.unittesting.remote.IoDispatcher
import com.example.unittesting.remote.MainDispatcher
import com.example.unittesting.remote.UnconfinedDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestCoroutinesModule {

    @Provides
    @Singleton
    @MainDispatcher
    fun provideTestMainDispatcher(): CoroutineDispatcher = IdlingDispatcher(Dispatchers.Main)

    @Provides
    @Singleton
    @IoDispatcher
    fun provideTestIoDispatcher(): CoroutineDispatcher = IdlingDispatcher(Dispatchers.IO)

    @Provides
    @Singleton
    @DefaultDispatcher
    fun provideTestDefaultDispatcher(): CoroutineDispatcher = IdlingDispatcher(Dispatchers.Default)

    @Provides
    @Singleton
    @UnconfinedDispatcher
    fun provideTestUnconfinedDispatcher(): CoroutineDispatcher =
        IdlingDispatcher(Dispatchers.Unconfined)

}