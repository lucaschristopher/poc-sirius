package com.example.pocsirius.core.network.interceptor

import com.example.pocsirius.core.common.security.SecurityProperties
import com.example.pocsirius.core.network.config.NetworkConfig
import com.example.pocsirius.core.network.session.SessionHandler
import com.example.pocsirius.core.network.session.SessionStorage
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionStorage: SessionStorage,
    private val sessionHandler: SessionHandler,
    private val networkConfig: NetworkConfig,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addDefaultHeaders()
            .addTokenHeader(sessionStorage)
            .build()

        val response = chain.proceed(request)
        val body = response.body
        val bodyString = body?.string().orEmpty()
        val contentType = body?.contentType()

        sessionHandler.handle(response.code)

        return response.newBuilder()
            .body(bodyString.toResponseBody(contentType))
            .build()
    }

    private fun Request.Builder.addDefaultHeaders() = apply {
        addHeader(SecurityProperties.Header.ACCEPT, SecurityProperties.Header.ACCEPT_VALUE)
        addHeader(SecurityProperties.Header.CONTENT_TYPE, SecurityProperties.Header.CONTENT_TYPE_VALUE)
        addHeader(SecurityProperties.Header.APP_VERSION, networkConfig.appVersion)
        addHeader(SecurityProperties.Header.MOBILE_ID, networkConfig.mobileId)
    }

    private fun Request.Builder.addTokenHeader(storage: SessionStorage) = apply {
        storage.getToken()?.let { token ->
            addHeader(
                SecurityProperties.Header.AUTHORIZATION,
                SecurityProperties.Header.BEARER.format(token)
            )
        }
    }
}
