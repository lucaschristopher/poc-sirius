package com.example.pocsirius.feature.auth.presentation.login

import com.example.pocsirius.core.common.presentation.UiAction

sealed interface LoginUiAction : UiAction {
    data class EmailChanged(val email: String) : LoginUiAction
    data class PasswordChanged(val password: String) : LoginUiAction
    data object LoginClicked : LoginUiAction
}
