package org.thindie.sensorReading.di

import dagger.BindsInstance
import dagger.Component
import org.thindie.sensorReading.CelsiusSensorsScope
import org.thindie.sensorReading.di.modules.ApiServiceModule
import org.thindie.sensorReading.di.modules.DispatchersModule
import org.thindie.sensorReading.di.modules.GsonConverterFactoryModule
import org.thindie.sensorReading.di.modules.RepositoryModule
import org.thindie.sensorReading.di.modules.RetrofitModule
import org.thindie.sensorReading.di.modules.ScopeModule
import org.thindie.sensorReading.di.modules.ValidatorModule
import org.thindie.sensorReading.domain.LibraryDefaults
import javax.inject.Singleton


@Component(
    modules =
    [DispatchersModule::class,
        RepositoryModule::class,
        ScopeModule::class, ApiServiceModule::class,
        RetrofitModule::class,
        ValidatorModule::class,
        GsonConverterFactoryModule::class]
)
@Singleton
internal interface LibraryComponent {
    companion object {
        fun init(): LibraryComponent {
            return DaggerLibraryComponent
                .factory()
                .create(LibraryDefaults.Network.BASE_URL, LibraryDefaults.Network.TIMEOUT, LibraryDefaults.isFakeLogicInsteadReal)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance baseUrl: String, @BindsInstance baseOperationsTimeOut: Long, @BindsInstance isFakeLogicInsteadReal: Boolean): LibraryComponent
    }

    fun inject(provider: CelsiusSensorsScope)
}