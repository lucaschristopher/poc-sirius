package com.example.pocsirius.feature.home.presentation.rotacoach

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pocsirius.core.common.error.Error

@Composable
fun RotaCoachScreen(
    viewModel: RotaCoachViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                RotaCoachUiEvent.NavigateBack -> onNavigateBack()
            }
        }
    }

    RotaCoachContent(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun RotaCoachContent(
    uiState: RotaCoachUiState,
    onAction: (RotaCoachUiAction) -> Unit,
) {
    when (uiState) {
        RotaCoachUiState.Loading -> RotaCoachLoadingContent()
        RotaCoachUiState.Content -> RotaCoachSuccessContent()
        is RotaCoachUiState.Failure -> RotaCoachErrorContent()
    }
}

@Composable
private fun RotaCoachLoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun RotaCoachSuccessContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Rota Coach",
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@Composable
private fun RotaCoachErrorContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Erro ao carregar",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RotaCoachLoadingPreview() {
    MaterialTheme {
        RotaCoachContent(
            uiState = RotaCoachUiState.Loading,
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RotaCoachContentPreview() {
    MaterialTheme {
        RotaCoachContent(
            uiState = RotaCoachUiState.Content,
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RotaCoachErrorPreview() {
    MaterialTheme {
        RotaCoachContent(
            uiState = RotaCoachUiState.Failure(
                error = Error(
                    title = "Erro",
                    message = "Erro ao carregar",
                    code = "500",
                )
            ),
            onAction = {},
        )
    }
}
