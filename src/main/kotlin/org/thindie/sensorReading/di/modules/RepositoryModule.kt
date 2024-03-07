package org.thindie.sensorReading.di.modules

import dagger.Binds
import dagger.Module
import org.thindie.sensorReading.data.SensorsReadingRepositoryImpl
import org.thindie.sensorReading.domain.SensorsReadingRepository

@Module
internal interface RepositoryModule {
    @Binds
    fun bindRepository(impl: SensorsReadingRepositoryImpl): SensorsReadingRepository<Double>
}