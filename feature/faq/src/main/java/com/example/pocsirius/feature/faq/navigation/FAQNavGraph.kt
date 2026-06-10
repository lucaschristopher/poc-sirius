package com.example.pocsirius.feature.faq.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pocsirius.core.navigation.FAQRoute
import com.example.pocsirius.feature.faq.presentation.FAQScreen

fun NavGraphBuilder.faqGraph(
    onNavigateBack: () -> Unit,
) {
    composable<FAQRoute> {
        FAQScreen(onNavigateBack = onNavigateBack)
    }
}
