package com.example.pocsirius.feature.home.presentation.home

import com.example.pocsirius.core.common.presentation.UiEvent

sealed interface HomeUiEvent : UiEvent {
    data object NavigateToConfigurations : HomeUiEvent
    data object NavigateToNotifications : HomeUiEvent
    data object NavigateToFAQ : HomeUiEvent
    data object NavigateToLogin : HomeUiEvent
}
