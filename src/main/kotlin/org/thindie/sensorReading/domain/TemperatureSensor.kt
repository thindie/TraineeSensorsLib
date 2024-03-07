package org.thindie.sensorReading.domain

interface TemperatureSensor<T> : SimpleSensor {
    fun getTemperature(): T
}