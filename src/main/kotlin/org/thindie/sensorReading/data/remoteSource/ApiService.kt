package org.thindie.sensorReading.data.remoteSource

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

internal interface ApiService {
    @GET
    suspend fun getTemperatureSensor(@Url url: String): Response<TemperatureSensorDto>
}