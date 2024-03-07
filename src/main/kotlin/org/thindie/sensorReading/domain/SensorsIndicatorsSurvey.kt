package org.thindie.sensorReading.domain


interface SensorsIndicatorsSurvey<T : SimpleSensor> {
    val sensorsList: List<T>
}