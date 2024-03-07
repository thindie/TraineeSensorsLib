package org.thindie.sensorReading.domain

interface SimpleSensor : Loggable {
    fun getId(): String
    fun getUrl(): String
}