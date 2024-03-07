package org.thindie.sensorReading

import org.thindie.sensorReading.di.LibraryComponent
import org.thindie.sensorReading.domain.SensorsReadingRepository
import javax.inject.Inject

/**
 * Dagger App Component; Dependency graph
 */
private val appComponent by lazy { LibraryComponent.init() }


internal val provider: CelsiusSensorsScope by lazy { CelsiusSensorsScope.getInstance() }
    .apply {
        appComponent.inject(value)
    }


internal class CelsiusSensorsScope private constructor() {

    private var _repository: SensorsReadingRepository<Double>? = null

    @Inject
    fun dependencyInjection(repository: SensorsReadingRepository<Double>) {
        if (_repository == null) _repository = repository
    }

    suspend fun getSensorsIndicatorsList(sensorIds: List<String>) =
        requireNotNull(_repository).getSensorsReadingsByUrls(sensorIds)

    fun interruptSurvey() = requireNotNull(_repository).abortSensorsReadingOperation()

    companion object {
        fun getInstance() = CelsiusSensorsScope()
    }
}