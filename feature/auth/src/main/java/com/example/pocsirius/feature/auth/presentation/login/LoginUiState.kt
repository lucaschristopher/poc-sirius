package com.example.pocsirius.feature.auth.presentation.login

import com.example.pocsirius.core.common.error.Error
import com.example.pocsirius.core.common.presentation.UiState

sealed class LoginUiState : UiState {
    data object Idle : LoginUiState()
    data object Loading : LoginUiState()
    data class Failure(val error: Error) : LoginUiState()
}