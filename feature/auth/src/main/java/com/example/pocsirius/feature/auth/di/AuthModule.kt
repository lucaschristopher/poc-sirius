package com.example.pocsirius.feature.auth.di

import com.example.pocsirius.core.common.extension.safeRunDispatcher
import com.example.pocsirius.feature.auth.data.repository.AuthRepositoryImpl
import com.example.pocsirius.feature.auth.domain.repository.AuthRepository
import com.example.pocsirius.feature.auth.domain.usecase.LoginUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl,
    ): AuthRepository

    companion object {
        @Provides
        fun provideLoginUseCase(
            repository: AuthRepository,
        ): LoginUseCase = LoginUseCase { email, password ->
            safeRunDispatcher {
                repository.login(email, password)
            }
        }
    }
}