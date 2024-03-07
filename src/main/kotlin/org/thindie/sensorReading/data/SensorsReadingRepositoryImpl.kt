package org.thindie.sensorReading.data

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.thindie.sensorReading.data.fake.FakeResponseProvider
import org.thindie.sensorReading.data.fake.fakeReadingProcess
import org.thindie.sensorReading.data.mapper.toTemperatureSensorEntity
import org.thindie.sensorReading.data.remoteSource.ApiService
import org.thindie.sensorReading.data.remoteSource.TemperatureSensorDto
import org.thindie.sensorReading.domain.LibraryDefaults
import org.thindie.sensorReading.domain.SensorsIndicatorsSurvey
import org.thindie.sensorReading.domain.SensorsReadingRepository
import org.thindie.sensorReading.domain.TemperatureSensor
import org.thindie.sensorReading.domain.TemperatureSensorEntity
import org.thindie.sensorReading.domain.Validator
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @param isFakeLogicInsteadReal is synthetic, change it in [LibraryDefaults], or delete to test with real data ;]
 * , see [getSensorsReadingsByUrls]
 *  */
@Singleton
internal class SensorsReadingRepositoryImpl
@Inject constructor(
    private val service: ApiService,
    private val scope: CoroutineScope,
    private val urlValidator: Validator,
    private val fake: FakeResponseProvider,
    private val isFakeLogicInsteadReal: Boolean,
) : SensorsReadingRepository<Double> {

    private val shouldContinueSensorsSurvey = MutableStateFlow(true)

    private val sensorIndicationsState = MutableStateFlow(emptyList<TemperatureSensorEntity>())

    override suspend fun getSensorsReadingsByUrls(sensorUrls: List<String>): SensorsIndicatorsSurvey<TemperatureSensor<Double>> {
        scope.launch {

            observeCancellingOperation(scope, shouldContinueSensorsSurvey)
            if (isFakeLogicInsteadReal){
                fakeReadingProcess(fake, sensorIndicationsState, scope = scope ,timeOut = 2000L)
            }
            else {
                sensorUrls.forEach { url ->
                    realSensorReadingProcess(
                        listState = sensorIndicationsState,
                        apiService = service,
                        url = url,
                        scope = this,
                        urlValidator = urlValidator
                    )
                }
            }
        }.join()

        return IndicatorSurveyEntity(sensorsList = sensorIndicationsState.value)
    }

    override fun abortSensorsReadingOperation() {
        shouldContinueSensorsSurvey.value = false
    }

    private fun observeCancellingOperation(scope: CoroutineScope, state: Flow<Boolean>){
        state
            .onEach { predicate ->
                if (!predicate) {
                    scope.cancel()
                }
            }
            .launchIn(scope)
    }

    private suspend fun realSensorReadingProcess(
        listState: MutableStateFlow<List<TemperatureSensorEntity>>,
        scope: CoroutineScope,
        apiService: ApiService,
        url: String,
        urlValidator: Validator
    ) {

        try {
            urlValidator.validate(url)
        } catch (_: IllegalArgumentException) {

            listState.update { currentStateList ->
                val safeUpdateableList = currentStateList.toMutableList()
                safeUpdateableList.add(
                    TemperatureSensorEntity.getErrorReadingInstance(
                        logMessage = LibraryDefaults.Message.WRONG_URL,
                      sensorUrl =   url
                    )
                )
                safeUpdateableList.toList()
            }
            return
        }

        val sensorResponseDeferred = scope.async { apiService.getTemperatureSensor(url) }

        try {
            val sensorResponse = sensorResponseDeferred.await()
            updateState(sensorResponse, listState, url)
        } catch (e: CancellationException) {
            TemperatureSensorEntity.getErrorReadingInstance(logMessage = LibraryDefaults.Message.TIMEOUT, sensorUrl = url)
        }

    }

    private fun updateState(
        sensorResponse: Response<TemperatureSensorDto>,
        listState: MutableStateFlow<List<TemperatureSensorEntity>>,
        url: String
    ) {
        if (sensorResponse.isSuccessful && sensorResponse.body() != null) {
            listState.update {
                val resultList = it.toMutableList()
                resultList.add(sensorResponse.body()!!.toTemperatureSensorEntity(url))
                resultList.toList()
            }
        }
    }
}


