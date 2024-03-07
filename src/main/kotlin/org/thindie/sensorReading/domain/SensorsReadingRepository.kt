package org.thindie.sensorReading.domain

internal interface SensorsReadingRepository<T> {

    suspend fun getSensorsReadingsByUrls(sensorUrls: List<String>): SensorsIndicatorsSurvey<TemperatureSensor<T>>
    fun abortSensorsReadingOperation()
}