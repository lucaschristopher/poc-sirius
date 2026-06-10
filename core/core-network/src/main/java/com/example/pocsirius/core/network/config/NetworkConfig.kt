package com.example.pocsirius.core.network.config

data class NetworkConfig(
    val baseUrl: String,
    val appVersion: String,
    val mobileId: String,
    val isLogEnabled: Boolean = true,
    val timeoutSeconds: Long = DEFAULT_TIMEOUT,
) {
    companion object {
        private const val DEFAULT_TIMEOUT = 30L
    }
}
