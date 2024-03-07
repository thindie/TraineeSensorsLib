package org.thindie.sensorReading.di.modules

import dagger.Binds
import dagger.Module
import org.thindie.sensorReading.data.remoteSource.UrlValidator
import org.thindie.sensorReading.domain.Validator

@Module
internal interface ValidatorModule {
    @Binds
    fun bindStringValidator(urlValidator: UrlValidator): Validator
}