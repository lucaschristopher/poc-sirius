package com.example.pocsirius.feature.home.presentation.rotacoach

import androidx.lifecycle.viewModelScope
import com.example.pocsirius.core.common.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RotaCoachViewModel @Inject constructor() :
    BaseViewModel<RotaCoachUiState, RotaCoachUiAction, RotaCoachUiEvent>(
        initialState = RotaCoachUiState.Loading,
    ) {

    init {
        load()
    }

    override fun onAction(action: RotaCoachUiAction) {
        when (action) {
            RotaCoachUiAction.NavigateBack ->
                sendEvent(RotaCoachUiEvent.NavigateBack)
        }
    }

    private fun load() {
        viewModelScope.launch {
            delay(1_500L)
            updateState { RotaCoachUiState.Content }
        }
    }
}
