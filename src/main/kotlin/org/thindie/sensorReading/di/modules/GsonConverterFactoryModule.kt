package org.thindie.sensorReading.di.modules

import dagger.Module
import dagger.Provides
import retrofit2.converter.gson.GsonConverterFactory

@Module
internal class GsonConverterFactoryModule {
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
}