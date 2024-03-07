package org.thindie.sensorReading.domain

internal interface Validator {
    fun validate(string: String): Boolean
}