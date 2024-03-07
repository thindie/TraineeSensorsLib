package org.thindie.sensorReading.di.modules

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
internal class RetrofitModule {
    @Provides
    fun provideRetrofit(factory: GsonConverterFactory, baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(factory)
            .build()
    }

    @Provides
    fun provideOkhttpClient(baseOperationsTimeOut: Long): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(baseOperationsTimeOut, TimeUnit.MILLISECONDS)
            .build()
    }
}