package com.example.pocsirius.feature.home.presentation.rotacoach

import com.example.pocsirius.core.common.presentation.UiAction

sealed interface RotaCoachUiAction : UiAction {
    data object NavigateBack : RotaCoachUiAction
}
