package com.example.unittesting.utils

import com.example.unittesting.di.CoroutinesModule
import com.example.unittesting.di.DefaultDispatcher
import com.example.unittesting.di.IoDispatcher
import com.example.unittesting.di.MainDispatcher
import com.example.unittesting.di.UnconfinedDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [CoroutinesModule::class])
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