package com.example.pocsirius.feature.auth.presentation.login

import com.example.pocsirius.core.common.presentation.UiEvent

sealed interface LoginUiEvent : UiEvent {
    data object NavigateToHome : LoginUiEvent
}
