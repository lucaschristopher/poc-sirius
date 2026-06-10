package com.example.pocsirius.feature.home.presentation.home

import com.example.pocsirius.core.common.error.Error
import com.example.pocsirius.core.common.presentation.UiState

sealed class HomeUiState : UiState {
    data object Loading : HomeUiState()
    data object Content : HomeUiState()
    data class Failure(val error: Error) : HomeUiState()
}