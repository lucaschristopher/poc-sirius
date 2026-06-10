package com.example.pocsirius.feature.home.presentation.home

import com.example.pocsirius.core.common.presentation.UiAction

sealed interface HomeUiAction : UiAction {
    data object NavigateToNotifications : HomeUiAction
    data object NavigateToConfigurations : HomeUiAction
    data object NavigateToFAQ : HomeUiAction
    data object Logout : HomeUiAction
}
