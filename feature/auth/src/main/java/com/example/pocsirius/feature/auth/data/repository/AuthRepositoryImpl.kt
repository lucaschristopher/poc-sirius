package com.example.pocsirius.feature.auth.data.repository

import com.example.pocsirius.core.network.session.SessionStorage
import com.example.pocsirius.feature.auth.data.api.AuthApi
import com.example.pocsirius.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject
import kotlin.random.Random

class AuthRepositoryImpl @Inject constructor(
    // private val api: AuthApi, -> aqui temos a injeção da API
    private val sessionStorage: SessionStorage,
) : AuthRepository {

    override suspend fun login(email: String, password: String) {
        delay(DELAY)
        if (Random.nextBoolean()) {
            sessionStorage.saveToken(MOCK_TOKEN)
        } else {
            throw HttpException(
                Response.error<Any>(
                    401,
                    "{}".toResponseBody("application/json".toMediaType())
                )
            )
        }
    }

    companion object {
        private const val DELAY = 1_500L
        private const val MOCK_TOKEN = "mock-token-sirius-supervisor"
    }
}