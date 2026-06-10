package com.example.pocsirius.core.common.security

object SecurityProperties {

    object Url {
        val API_SIRIUS = when (BuildType.current) {
            BuildType.RELEASE -> "https://api.siriussupervisor.com.br/"
            BuildType.DEBUG -> "https://hlg-api.siriussupervisor.com.br/"
            BuildType.MOCK -> "https://mock-api.siriussupervisor.com.br/"
            else -> "https://hlg-api.siriussupervisor.com.br/"
        }
    }

    object Header {
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer %s"
        const val APP_VERSION = "X-App-Version"
        const val MOBILE_ID = "X-Mobile-Id"
        const val ACCEPT = "Accept"
        const val CONTENT_TYPE = "Content-Type"
        const val ACCEPT_VALUE = "application/json"
        const val CONTENT_TYPE_VALUE = "application/json"
    }
}