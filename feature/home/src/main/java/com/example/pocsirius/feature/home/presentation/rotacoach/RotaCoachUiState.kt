package com.example.pocsirius.feature.home.presentation.rotacoach

import com.example.pocsirius.core.common.error.Error
import com.example.pocsirius.core.common.presentation.UiState

sealed class RotaCoachUiState : UiState {
    data object Loading : RotaCoachUiState()
    data object Content : RotaCoachUiState()
    data class Failure(val error: Error) : RotaCoachUiState()
}
