package com.example.pocsirius.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pocsirius.core.navigation.ForgotPasswordRoute
import com.example.pocsirius.core.navigation.LoginRoute
import com.example.pocsirius.core.navigation.RegisterRoute
import com.example.pocsirius.feature.auth.presentation.forgotpassword.ForgotPasswordScreen
import com.example.pocsirius.feature.auth.presentation.login.LoginScreen
import com.example.pocsirius.feature.auth.presentation.register.RegisterScreen

fun NavGraphBuilder.authGraph(
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    composable<LoginRoute> {
        LoginScreen(
            onNavigateToHome = onNavigateToHome,
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToForgotPassword = onNavigateToForgotPassword,
        )
    }
    composable<RegisterRoute> {
        RegisterScreen(
            onNavigateBack = onNavigateBack,
        )
    }
    composable<ForgotPasswordRoute> {
        ForgotPasswordScreen(
            onNavigateBack = onNavigateBack,
        )
    }
}