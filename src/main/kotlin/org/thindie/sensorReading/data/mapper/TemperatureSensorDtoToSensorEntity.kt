package org.thindie.sensorReading.data.mapper

import org.thindie.sensorReading.data.remoteSource.TemperatureSensorDto
import org.thindie.sensorReading.domain.TemperatureSensorEntity

internal fun TemperatureSensorDto.toTemperatureSensorEntity(url: String) = TemperatureSensorEntity(
    sensorId = id,
    sensorTemperature = temperatureCelsius,
    sensorUrl = url,
)