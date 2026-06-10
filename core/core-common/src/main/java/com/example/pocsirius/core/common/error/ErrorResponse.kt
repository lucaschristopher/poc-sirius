package com.example.pocsirius.core.common.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("title") val title: String? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("code") val code: String? = null,
) {
    fun toError(default: Error = ErrorHandler.default): Error =
        Error(
            title = title ?: default.title,
            message = message ?: default.message,
            code = code ?: default.code,
        )
}
