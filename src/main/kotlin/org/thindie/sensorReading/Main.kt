package org.thindie.sensorReading

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * synthetic logic invocation
 */

private suspend fun main() {
    val scope = CoroutineScope(Job())
    scope.launch {
        fakeAbortion()
    }
    scope.launch {
        fakeReadingRequest()
    }.join()

}

/**
 * u can play with delay duration
 */
private suspend fun fakeAbortion() {
    delay(3200)
    abortSensorsReadingOperation()
}

/**
 * вращайте барабан
 */
private suspend fun fakeReadingRequest() {
    getSensorsReadingsByUrls(arrayOf())
        .sensorsList
        .forEach { sensor ->
            println("${sensor.getTemperature()} ${sensor.getLog()}")
        }
}