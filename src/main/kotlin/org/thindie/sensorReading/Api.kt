package org.thindie.sensorReading

import org.thindie.sensorReading.domain.SensorsIndicatorsSurvey
import org.thindie.sensorReading.domain.TemperatureSensor

/**
 * get Sensors data by Url
 */

suspend fun getSensorsReadingsByUrls(sensorUrls: Array<String>): SensorsIndicatorsSurvey<TemperatureSensor<Double>> {
    return provider.getSensorsIndicatorsList(sensorUrls.toList())
}

/**
 * getting Cancellation
 */

fun abortSensorsReadingOperation() = provider.interruptSurvey()


/**
 * By default, TimeOut fetching abortion being manage by Retrofit OKHTTP client
 */