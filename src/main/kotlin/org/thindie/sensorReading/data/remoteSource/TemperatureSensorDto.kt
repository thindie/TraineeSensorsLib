package org.thindie.sensorReading.data.remoteSource

import com.google.gson.annotations.SerializedName

internal data class TemperatureSensorDto(
    @SerializedName("sensorId") val id: String,
    @SerializedName("temperatureC") val temperatureCelsius: Double
)