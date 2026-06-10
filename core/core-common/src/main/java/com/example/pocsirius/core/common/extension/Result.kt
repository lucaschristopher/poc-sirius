package com.example.pocsirius.core.common.extension

import com.example.pocsirius.core.common.error.Error

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val error: Error) : Result<Nothing>()
}

fun <T> Result<T>.fold(
    onSuccess: (T) -> Unit,
    onFailure: (Error) -> Unit,
) = when (this) {
    is Result.Success -> onSuccess(data)
    is Result.Failure -> onFailure(error)
}

fun <T> Result<T>.onSuccess(onSuccess: (T) -> Unit): Result<T> {
    if (this is Result.Success) onSuccess(data)
    return this
}

fun <T> Result<T>.getOrNull(): T? = when (this) {
    is Result.Success -> data
    is Result.Failure -> null
}

fun <T> Result<T>.errorOrNull(): Error? = when (this) {
    is Result.Success -> null
    is Result.Failure -> error
}

fun <T> Result<T>.getOrThrow(): T = when (this) {
    is Result.Success -> data
    is Result.Failure -> throw SafeRunException(error = error)
}

fun <T> Result<T>.throwOnFailure() {
    if (this is Result.Failure) throw SafeRunException(error = error)
}

fun <T> Result<T>.isSuccess(): Boolean = this is Result.Success
fun <T> Result<T>.isFailure(): Boolean = this is Result.Failure