package org.thindie.sensorReading.data.remoteSource

import org.thindie.sensorReading.domain.Validator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UrlValidator @Inject constructor() : Validator {
    override fun validate(string: String): Boolean {

        //some regexp logic for url validation
        require(true)
        return true
    }
}

