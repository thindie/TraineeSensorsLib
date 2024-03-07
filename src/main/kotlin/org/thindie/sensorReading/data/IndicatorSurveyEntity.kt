package org.thindie.sensorReading.data

import org.thindie.sensorReading.domain.SensorsIndicatorsSurvey
import org.thindie.sensorReading.domain.TemperatureSensor

internal data class IndicatorSurveyEntity(
    override val sensorsList: List<TemperatureSensor<Double>>,
) : SensorsIndicatorsSurvey<TemperatureSensor<Double>>