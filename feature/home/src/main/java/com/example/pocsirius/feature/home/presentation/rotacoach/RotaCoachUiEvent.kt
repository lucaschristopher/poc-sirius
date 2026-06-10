package com.example.pocsirius.feature.home.presentation.rotacoach

import com.example.pocsirius.core.common.presentation.UiEvent

sealed interface RotaCoachUiEvent : UiEvent {
    data object NavigateBack : RotaCoachUiEvent
}
