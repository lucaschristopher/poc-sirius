package com.example.pocsirius.core.network.session

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionEventBus @Inject constructor() {

    private val _events = MutableSharedFlow<SessionEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<SessionEvent> = _events

    fun emit(event: SessionEvent) {
        _events.tryEmit(event)
    }
}
