package org.thindie.sensorReading.di.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob

@Module
internal class ScopeModule {
    @Provides
    fun provideCoroutineScope(@IODispatcherProvider ioDispatcher: CoroutineDispatcher): CoroutineScope {
        return CoroutineScope(Job() + ioDispatcher)
    }
}