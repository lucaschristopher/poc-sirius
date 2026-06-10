package com.example.pocsirius.feature.home.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pocsirius.core.common.error.Error
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToNotifications: () -> Unit,
    onNavigateToConfigurations: () -> Unit,
    onNavigateToFAQ: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                HomeUiEvent.NavigateToNotifications -> onNavigateToNotifications()
                HomeUiEvent.NavigateToConfigurations -> onNavigateToConfigurations()
                HomeUiEvent.NavigateToFAQ -> onNavigateToFAQ()
                HomeUiEvent.NavigateToLogin -> onNavigateToLogin()
            }
        }
    }

    HomeContent(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeContent(
    uiState: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedTab by remember { mutableIntStateOf(0) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                // Header — foto + nome + cargo
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "John Doe",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = "Supervisor",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                HorizontalDivider()

                // Notificações → navega pra fora
                NavigationDrawerItem(
                    label = { Text("Notificações") },
                    selected = false,
                    icon = {
                        Icon(Icons.Default.Notifications, contentDescription = null)
                    },
                    onClick = {
                        scope.launch { drawerState.close() }
                        onAction(HomeUiAction.NavigateToNotifications)
                    }
                )

                // Configurações → navega pra fora
                NavigationDrawerItem(
                    label = { Text("Configurações") },
                    selected = false,
                    icon = {
                        Icon(Icons.Default.Settings, contentDescription = null)
                    },
                    onClick = {
                        scope.launch { drawerState.close() }
                        onAction(HomeUiAction.NavigateToConfigurations)
                    }
                )

                // FAQ → navega pra fora
                NavigationDrawerItem(
                    label = { Text("Fale Conosco") },
                    selected = false,
                    icon = {
                        Icon(Icons.Default.Call, contentDescription = null)
                    },
                    onClick = {
                        scope.launch { drawerState.close() }
                        onAction(HomeUiAction.NavigateToFAQ)
                    }
                )

                HorizontalDivider()

                // Logout
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "Fazer logout",
                            color = MaterialTheme.colorScheme.error,
                        )
                    },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                        )
                    },
                    onClick = {
                        scope.launch { drawerState.close() }
                        onAction(HomeUiAction.Logout)
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Olá, John Doe",
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = "Supervisor",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menu",
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            onAction(HomeUiAction.NavigateToNotifications)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notificações",
                            )
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = {
                            Icon(Icons.Default.Home, contentDescription = null)
                        },
                        label = { Text("Home") },
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = {
                            Icon(Icons.Default.ShoppingCart, contentDescription = null)
                        },
                        label = { Text("Rota Coach") },
                    )
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                when (uiState) {
                    HomeUiState.Loading -> HomeLoadingContent()
                    is HomeUiState.Failure -> HomeErrorContent()
                    HomeUiState.Content -> when (selectedTab) {
                        0 -> HomeTabContent()
                        1 -> RotaCoachTabContent()
                        else -> HomeTabContent()
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeLoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun HomeErrorContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Erro ao carregar",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

@Composable
private fun HomeTabContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Home",
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = "Conteúdo da tela Home",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun RotaCoachTabContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Rota Coach",
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = "Conteúdo da tela Rota Coach",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

// ---- Previews ----

@Preview(showBackground = true, name = "Home Loading")
@Composable
private fun HomeLoadingPreview() {
    MaterialTheme {
        HomeContent(
            uiState = HomeUiState.Loading,
            onAction = {},
        )
    }
}

@Preview(showBackground = true, name = "Home Content")
@Composable
private fun HomeContentPreview() {
    MaterialTheme {
        HomeContent(
            uiState = HomeUiState.Content,
            onAction = {},
        )
    }
}

@Preview(showBackground = true, name = "Home Error")
@Composable
private fun HomeErrorPreview() {
    MaterialTheme {
        HomeContent(
            uiState = HomeUiState.Failure(
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
