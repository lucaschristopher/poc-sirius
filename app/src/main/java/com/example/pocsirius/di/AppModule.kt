package com.example.pocsirius.di

import android.content.Context
import android.provider.Settings
import com.example.pocsirius.BuildConfig
import com.example.pocsirius.core.common.security.BuildType
import com.example.pocsirius.core.common.security.SecurityProperties
import com.example.pocsirius.core.network.config.NetworkConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkConfig(
        @ApplicationContext context: Context,
    ): NetworkConfig = NetworkConfig(
        baseUrl = SecurityProperties.Url.API_SIRIUS,
        appVersion = BuildConfig.VERSION_NAME,
        mobileId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID,
        ) ?: "unknown-device",
        isLogEnabled = BuildType.isDebug,
        timeoutSeconds = if (BuildType.isRelease) 30L else 60L,
    )
}