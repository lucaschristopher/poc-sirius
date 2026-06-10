package com.example.pocsirius.feature.auth.presentation.login

import androidx.lifecycle.viewModelScope
import com.example.pocsirius.core.common.error.Error
import com.example.pocsirius.core.common.extension.fold
import com.example.pocsirius.core.common.presentation.BaseViewModel
import com.example.pocsirius.feature.auth.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginUiState, LoginUiAction, LoginUiEvent>(
    initialState = LoginUiState.Idle,
) {
    private var email = ""
    private var password = ""

    override fun onAction(action: LoginUiAction) {
        when (action) {
            is LoginUiAction.EmailChanged -> email = action.email
            is LoginUiAction.PasswordChanged -> password = action.password
            LoginUiAction.LoginClicked -> login()
        }
    }

    private fun login() = viewModelScope.launch {
        if (!isValid()) return@launch

        updateState { LoginUiState.Loading }

        loginUseCase(
            email = email,
            password = password,
        ).fold(
            onSuccess = { sendEvent(LoginUiEvent.NavigateToHome) },
            onFailure = { error -> updateState { LoginUiState.Failure(error) } },
        )
    }

    private fun isValid(): Boolean {
        if (email.isBlank() || password.isBlank()) {
            updateState {
                LoginUiState.Failure(
                    error = Error(
                        title = "Campos obrigatórios",
                        message = "E-mail e senha são obrigatórios.",
                        code = "400",
                    )
                )
            }
            return false
        }
        return true
    }
}
