package com.example.pocsirius.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.pocsirius.core.navigation.ConfigurationsRoute
import com.example.pocsirius.core.navigation.FAQRoute
import com.example.pocsirius.core.navigation.ForgotPasswordRoute
import com.example.pocsirius.core.navigation.HomeRoute
import com.example.pocsirius.core.navigation.LoginRoute
import com.example.pocsirius.core.navigation.NotificationsRoute
import com.example.pocsirius.core.navigation.RegisterRoute
import com.example.pocsirius.feature.auth.navigation.authGraph
import com.example.pocsirius.feature.home.navigation.homeGraph
import com.example.pocsirius.feature.faq.navigation.faqGraph

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = LoginRoute,
    ) {
        authGraph(
            onNavigateToHome = {
                navController.navigate(HomeRoute) {
                    popUpTo(LoginRoute) { inclusive = true }
                }
            },
            onNavigateToRegister = {
                navController.navigate(RegisterRoute)
            },
            onNavigateToForgotPassword = {
                navController.navigate(ForgotPasswordRoute)
            },
            onNavigateBack = {
                navController.popBackStack()
            }
        )

        homeGraph(
            onNavigateToNotifications = {
                navController.navigate(NotificationsRoute)
            },
            onNavigateToConfigurations = {
                navController.navigate(ConfigurationsRoute)
            },
            onNavigateToFAQ = {
                navController.navigate(FAQRoute)
            },
            onNavigateToLogin = {
                navController.navigate(LoginRoute) {
                    popUpTo(HomeRoute) { inclusive = true }
                }
            },
            onNavigateBack = {
                navController.popBackStack()
            }
        )

        faqGraph(
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }
}