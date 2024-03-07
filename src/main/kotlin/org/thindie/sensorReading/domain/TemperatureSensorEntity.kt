package org.thindie.sensorReading.domain

internal data class TemperatureSensorEntity(
    val sensorId: String,
    val sensorTemperature: Double,
    val sensorUrl: String,
    private val logMessage: String = LibraryDefaults.Sensor.EMPTY_LOG_MESSAGE
) : TemperatureSensor<Double> {
    override fun getId() = sensorId

    override fun getUrl() = sensorUrl

    override fun getLog() = logMessage

    override fun getTemperature() = sensorTemperature

    companion object {
        fun getErrorReadingInstance(logMessage: String, sensorUrl: String): TemperatureSensorEntity {
            return TemperatureSensorEntity(
                sensorId = LibraryDefaults.Sensor.UNDEFINED_ID,
                sensorTemperature = LibraryDefaults.Sensor.UNDEFINED_TEMPERATURE,
                logMessage = logMessage, sensorUrl = sensorUrl
            )
        }
    }
}