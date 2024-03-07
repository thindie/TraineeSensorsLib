package org.thindie.sensorReading.data.fake

import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.ResponseBody
import org.thindie.sensorReading.data.mapper.httpCodeMapper
import org.thindie.sensorReading.data.mapper.toTemperatureSensorEntity
import org.thindie.sensorReading.data.remoteSource.TemperatureSensorDto
import org.thindie.sensorReading.domain.LibraryDefaults
import org.thindie.sensorReading.domain.TemperatureSensorEntity

import retrofit2.Response
import javax.inject.Inject
import kotlin.random.Random

internal class FakeResponseProvider @Inject constructor(scope: CoroutineScope) {

    private val fakeJobsCompletedJson = arrayOf(scope.async {
        delay(getRandomBoundaryLong())
        json1
    }, scope.async {
        delay(getRandomBoundaryLong())
        json2
    }, scope.async {
        delay(getRandomBoundaryLong())
        json3
    }, scope.async {
        delay(getRandomBoundaryLong())
        json4
    }, scope.async {
        delay(getRandomBoundaryLong())
        json5
    })


    fun getFakeJobsList(): List<Deferred<String>> {
        return fakeJobsCompletedJson.toList()
    }


    suspend fun fakeDeferredCompletionOrCancellation(deferred: Deferred<String>): Response<String> {

        return try {
            Response.success(deferred.await())
        } catch (e: CancellationException) {
            Response.error(LibraryDefaults.Fake.TIME_OUT, ResponseBody.create(null, LibraryDefaults.Fake.ERROR_TIMEOUT_BODY))
        }


    }


    companion object {
        private val json1 = "{\"sensorId\": \"5d316ee8-a785-4e87-91d8-06f901c98a88\",\"temperatureC\": 22.4 }"
        private val json2 = "{\"sensorId\": \"5d316ee8-a785-4e87-91d8-06f901c98a88\",\"temperatureC\": 33.4 }"
        private val json3 = "{\"sensorId\": \"5d316ee8-a785-4e87-91d8-06f901c98a88\",\"temperatureC\": 44.4 }"
        private val json4 = "{\"sensorId\": \"5d316ee8-a785-4e87-91d8-06f901c98a88\",\"temperatureC\": 55.4 }"
        private val json5 = "non parcelable"
    }

}


internal fun Response<String>.getTemperatureSensor(): TemperatureSensorEntity {
    return if (isSuccessful) {
        val body = body()
        if (body != null) {
            try {
                Gson().fromJson(body, TemperatureSensorDto::class.java).toTemperatureSensorEntity("")
            } catch (e: Exception) {
                TemperatureSensorEntity.getErrorReadingInstance(
                    logMessage = e::class.java.toString().plus(" ").plus(body.toString()), sensorUrl = ""
                )
            }

        } else TemperatureSensorEntity.getErrorReadingInstance(logMessage = getErrorMessage(this), sensorUrl = "")
    } else {
        TemperatureSensorEntity.getErrorReadingInstance(logMessage = getErrorMessage(this), sensorUrl = "")
    }
}

internal suspend fun fakeReadingProcess(
    fake: FakeResponseProvider, sensorReadingsList: MutableStateFlow<List<TemperatureSensorEntity>>, timeOut: Long, scope: CoroutineScope
) {
    val list = fake.getFakeJobsList()

    list.forEach { deferred ->

        sensorReadingsList.update {
            val responseFromDeferred = fake.fakeDeferredCompletionOrCancellation(deferred)
            val mutableList = it.toMutableList()
            mutableList.add(
                responseFromDeferred.getTemperatureSensor()
            )
            mutableList.toList()
        }
    }
}

internal fun getErrorMessage(response: Response<String>): String {
    return response.code().httpCodeMapper()
}

private fun getRandomBoundaryLong() = Random.nextLong(
    LibraryDefaults.Fake.MIN_POSSIBLE_RANDOM_MILLIS_VALUE, LibraryDefaults.Fake.MAX_POSSIBLE_RANDOM_MILLIS_VALUE
)