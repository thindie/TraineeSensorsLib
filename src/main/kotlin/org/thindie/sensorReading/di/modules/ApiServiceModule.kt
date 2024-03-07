package org.thindie.sensorReading.di.modules

import dagger.Module
import dagger.Provides
import org.thindie.sensorReading.data.remoteSource.ApiService
import retrofit2.Retrofit

@Module
internal class ApiServiceModule {
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}