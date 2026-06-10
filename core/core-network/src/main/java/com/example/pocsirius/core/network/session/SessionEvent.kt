package com.example.pocsirius.core.network.session

sealed class SessionEvent {
    data object Expired : SessionEvent()
    data object Logout : SessionEvent()
}
