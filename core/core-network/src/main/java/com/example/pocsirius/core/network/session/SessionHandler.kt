package com.example.pocsirius.core.network.session

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionHandler @Inject constructor(
    private val sessionStorage: SessionStorage,
    private val sessionEventBus: SessionEventBus,
) {
    fun handle(responseCode: Int) {
        if (responseCode == HTTP_UNAUTHORIZED) {
            sessionStorage.clearSession()
            sessionEventBus.emit(SessionEvent.Expired)
        }
    }

    companion object {
        private const val HTTP_UNAUTHORIZED = 401
    }
}
