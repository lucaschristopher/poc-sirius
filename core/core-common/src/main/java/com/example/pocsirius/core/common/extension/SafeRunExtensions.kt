package com.example.pocsirius.core.common.extension

import com.example.pocsirius.core.common.error.Error
import com.example.pocsirius.core.common.error.ErrorHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

suspend fun <T> safeRunDispatcher(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend CoroutineScope.() -> T,
): Result<T> = withContext(dispatcher) {
    runCatching {
        Result.Success(data = block())
    }.getOrElse { throwable ->
        Timber.e("[safeRunDispatcher] $throwable")
        Result.Failure(error = ErrorHandler.convert(throwable))
    }
}

fun <T> safeRunCatching(block: () -> T): Result<T> =
    runCatching {
        Result.Success(data = block())
    }.getOrElse { throwable ->
        Result.Failure(error = ErrorHandler.convert(throwable))
    }

class SafeRunException(val error: Error) : Exception()
