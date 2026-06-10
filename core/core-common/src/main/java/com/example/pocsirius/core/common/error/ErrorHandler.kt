package com.example.pocsirius.core.common.error

import com.example.pocsirius.core.common.extension.SafeRunException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorHandler {

    private const val UNEXPECTED_ERROR_CODE = "500"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    val default = Error(
        title = "Algo deu errado por aqui",
        message = "Já estamos resolvendo. Aguarde alguns instantes e tente novamente.",
        code = UNEXPECTED_ERROR_CODE,
    )

    val timeoutError = Error(
        title = "Sem internet no momento",
        message = "Confira se o Wi-Fi ou os dados móveis estão funcionando e tente novamente.",
        code = UNEXPECTED_ERROR_CODE,
    )

    val connectivityError = timeoutError

    fun convert(throwable: Throwable): Error = when (throwable) {
        is SocketTimeoutException -> timeoutError
        is InterruptedIOException -> timeoutError
        is UnknownHostException -> connectivityError
        is SafeRunException -> throwable.error
        is HttpException -> convertHttpException(throwable)
        else -> default
    }

    private fun convertHttpException(exception: HttpException): Error =
        runCatching {
            val code = exception.code()
            if (code == UNEXPECTED_ERROR_CODE.toInt()) {
                default
            } else {
                exception.response()
                    ?.errorBody()
                    ?.string()
                    ?.let { json.decodeFromString<ErrorResponse>(it) }
                    ?.toError()
                    ?: default
            }
        }.getOrElse { default }
}
