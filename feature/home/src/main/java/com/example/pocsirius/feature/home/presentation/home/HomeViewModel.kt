package com.example.pocsirius.feature.home.presentation.home

import androidx.lifecycle.viewModelScope
import com.example.pocsirius.core.common.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() :
    BaseViewModel<HomeUiState, HomeUiAction, HomeUiEvent>(
        initialState = HomeUiState.Loading,
    ) {

    init {
        load()
    }

    override fun onAction(action: HomeUiAction) {
        when (action) {
            HomeUiAction.NavigateToNotifications -> sendEvent(HomeUiEvent.NavigateToNotifications)
            HomeUiAction.NavigateToConfigurations -> sendEvent(HomeUiEvent.NavigateToConfigurations)
            HomeUiAction.NavigateToFAQ -> sendEvent(HomeUiEvent.NavigateToFAQ)
            HomeUiAction.Logout -> sendEvent(HomeUiEvent.NavigateToLogin)
        }
    }

    private fun load() {
        viewModelScope.launch {
            delay(1_500L) // simula carregamento
            updateState { HomeUiState.Content }
        }
    }
}
