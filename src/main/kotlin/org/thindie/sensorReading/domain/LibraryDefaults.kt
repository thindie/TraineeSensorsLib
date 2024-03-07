package org.thindie.sensorReading.domain

internal object LibraryDefaults {

    const val isFakeLogicInsteadReal: Boolean = true

    object Network {
        const val TIMEOUT = 15_000L
        //synthetic, for retrofit being compilable. Respect KODE ;]
        const val BASE_URL = "https:/kode.ru"
    }

    object Sensor {
        const val UNDEFINED_ID = "undefined id"
        const val UNDEFINED_TEMPERATURE = Double.NaN
        const val EMPTY_LOG_MESSAGE = ""
    }

    object Fake {
        const val MIN_POSSIBLE_RANDOM_MILLIS_VALUE = 1000L
        const val MAX_POSSIBLE_RANDOM_MILLIS_VALUE = 4000L
        const val CODE = 500
        const val TIME_OUT = 600
        const val ERROR_TIMEOUT_BODY = "cancelled by timeout"
    }

    object Message {

        const val CLIENT_ERROR = "Client Error"
        const val SERVER_ERROR = "No Information from Sensor"
        const val REDIRECTION = "Redirection"
        const val SUCCESS = "success"
        const val STUB = ""
        const val TIMEOUT = "No Sensor data due Timeout"
        const val WRONG_URL = "Supplied Sensor Url is Bad / broken"
    }
}