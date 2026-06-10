package com.example.pocsirius.core.network.di

import com.example.pocsirius.core.network.config.NetworkConfig
import com.example.pocsirius.core.network.interceptor.AuthInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").d(message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
            redactHeader("Authorization")
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        networkConfig: NetworkConfig,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptorIfEnabled(
                enabled = networkConfig.isLogEnabled,
                interceptor = loggingInterceptor,
            )
            .connectTimeout(networkConfig.timeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(networkConfig.timeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(networkConfig.timeoutSeconds, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
        networkConfig: NetworkConfig,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(networkConfig.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            .build()

    private fun OkHttpClient.Builder.addInterceptorIfEnabled(
        enabled: Boolean,
        interceptor: Interceptor,
    ) = apply { if (enabled) addInterceptor(interceptor) }
}
