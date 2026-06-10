package com.example.pocsirius.feature.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pocsirius.core.common.error.Error

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                LoginUiEvent.NavigateToHome -> onNavigateToHome()
            }
        }
    }

    LoginContent(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun LoginContent(
    uiState: LoginUiState,
    onAction: (LoginUiAction) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Sirius Supervisor",
            style = MaterialTheme.typography.headlineMedium,
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                onAction(LoginUiAction.EmailChanged(it))
            },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                onAction(LoginUiAction.PasswordChanged(it))
            },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState is LoginUiState.Failure) {
            Text(
                text = uiState.error.message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onAction(LoginUiAction.LoginClicked) },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState !is LoginUiState.Loading,
        ) {
            if (uiState is LoginUiState.Loading) {
                CircularProgressIndicator()
            } else {
                Text("Entrar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginContentIdlePreview() {
    MaterialTheme {
        LoginContent(
            uiState = LoginUiState.Idle,
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginContentLoadingPreview() {
    MaterialTheme {
        LoginContent(
            uiState = LoginUiState.Loading,
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginContentErrorPreview() {
    MaterialTheme {
        LoginContent(
            uiState = LoginUiState.Failure(
                error = Error(
                    title = "Erro",
                    message = "Credenciais inválidas",
                    code = "401",
                )
            ),
            onAction = {},
        )
    }
}
