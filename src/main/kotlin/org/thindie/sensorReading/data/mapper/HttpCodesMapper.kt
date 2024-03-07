package org.thindie.sensorReading.data.mapper

import org.thindie.sensorReading.domain.LibraryDefaults

fun Int.httpCodeMapper(): String = when (this) {
    LibraryDefaults.Fake.TIME_OUT -> LibraryDefaults.Message.TIMEOUT
    in 99..199 -> LibraryDefaults.Message.STUB
    in 200..299 -> LibraryDefaults.Message.SUCCESS
    in 300..399 -> LibraryDefaults.Message.REDIRECTION
    in 400..499 -> LibraryDefaults.Message.CLIENT_ERROR
    in 500..599 -> LibraryDefaults.Message.SERVER_ERROR
    else -> LibraryDefaults.Message.STUB
}