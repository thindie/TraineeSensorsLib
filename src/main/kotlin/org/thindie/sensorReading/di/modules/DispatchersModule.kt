package org.thindie.sensorReading.di.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
internal class DispatchersModule {

    @Provides
    @IODispatcherProvider
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}

