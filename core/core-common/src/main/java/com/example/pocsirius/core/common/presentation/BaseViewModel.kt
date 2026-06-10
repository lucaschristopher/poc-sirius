package com.example.pocsirius.core.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : UiState, A : UiAction, E : UiEvent>(
    initialState: S,
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    protected val currentState: S
        get() = _uiState.value

    private val _uiEvent = Channel<E>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    abstract fun onAction(action: A)

    protected fun updateState(reducer: (S) -> S) {
        _uiState.value = reducer(_uiState.value)
    }

    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}