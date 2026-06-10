package com.example.pocsirius.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pocsirius.core.navigation.ConfigurationsRoute
import com.example.pocsirius.core.navigation.HomeRoute
import com.example.pocsirius.core.navigation.NotificationsRoute
import com.example.pocsirius.core.navigation.RotaCoachRoute
import com.example.pocsirius.feature.home.presentation.configurations.ConfigurationsScreen
import com.example.pocsirius.feature.home.presentation.home.HomeScreen
import com.example.pocsirius.feature.home.presentation.notifications.NotificationsScreen
import com.example.pocsirius.feature.home.presentation.rotacoach.RotaCoachScreen

fun NavGraphBuilder.homeGraph(
    onNavigateToNotifications: () -> Unit,
    onNavigateToConfigurations: () -> Unit,
    onNavigateToFAQ: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    composable<HomeRoute> {
        HomeScreen(
            onNavigateToNotifications = onNavigateToNotifications,
            onNavigateToConfigurations = onNavigateToConfigurations,
            onNavigateToFAQ = onNavigateToFAQ,
            onNavigateToLogin = onNavigateToLogin,
        )
    }
    composable<RotaCoachRoute> {
        RotaCoachScreen(
            onNavigateBack = onNavigateBack,
        )
    }
    composable<NotificationsRoute> {
        NotificationsScreen(onNavigateBack = onNavigateBack)
    }
    composable<ConfigurationsRoute> {
        ConfigurationsScreen(onNavigateBack = onNavigateBack)
    }
}
