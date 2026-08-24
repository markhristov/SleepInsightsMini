package com.example.sleepinsightsmini

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.sleepinsightsmini.features.createpredictor.CreatePredictorScreen
import com.example.sleepinsightsmini.features.insights.InsightsScreen
import com.example.sleepinsightsmini.features.log.ui.LogScreen
import com.example.sleepinsightsmini.features.log.LogViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Log : NavKey

@Serializable
data object CreatePredictor : NavKey

@Serializable
data object Insights : NavKey

@Composable
fun SleepInsightsMiniApp(logViewModel: LogViewModel = viewModel(factory = LogViewModel.Factory)) {

    val backStack = rememberNavBackStack(Log)
    val snackbarHostState = remember { SnackbarHostState() }

    fun navigateTop(destination: NavKey) {
        backStack.clear()
        backStack.add(Log)
        if (destination != Log) {
            backStack.add(destination)
        }
    }

    fun navigateBack() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }

    val logUiState by logViewModel.uiState.collectAsStateWithLifecycle()
    val checkedPredictors by logViewModel.selectedPredictors.collectAsStateWithLifecycle()

    val currentDestination = backStack.lastOrNull()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                name = when (currentDestination) {
                    Log -> "Daily log"
                    CreatePredictor -> "New predictor"
                    Insights -> "Insights"
                    else -> "Sleep Insights"
                },
                showBackButton = currentDestination == CreatePredictor,
                onBack = ::navigateBack,
            )
        },
        bottomBar = {
            if (currentDestination != CreatePredictor) {
                BottomNavBar(
                    currentDestination = currentDestination,
                    onLogClick = { navigateTop(Log) },
                    onInsightsClick = { navigateTop(Insights) },
                )
            }
        },
        floatingActionButton = {
            if (currentDestination == Log) {
                FloatingActionButton(onClick = { navigateTop(CreatePredictor) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add predictor",
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack, onBack = ::navigateBack, entryProvider = entryProvider {
                entry<Log> {
                    LogScreen(
                        predictors = logUiState.predictors,
                        onSubmit = logViewModel::onSubmitEntries,
                        checkedPredictors = checkedPredictors,
                        onCheckPredictor = logViewModel::onPredictorChecked,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
                entry<CreatePredictor> {
                    CreatePredictorScreen(
                        onSubmit = {
                            logViewModel.createPredictor(it)
                            navigateTop(Log)
                        },
                        onCancel = { navigateTop(Log) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                entry<Insights> {
                    InsightsScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    name: String,
    showBackButton: Boolean,
    onBack: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = name) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
        },
    )
}

@Composable
fun BottomNavBar(
    currentDestination: NavKey?,
    onLogClick: () -> Unit, onInsightsClick: () -> Unit, modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        NavigationBarItem(
            label = { Text("Log") },
            onClick = onLogClick,
            selected = currentDestination == Log,
            icon = {
                Icon(
                    imageVector = Icons.Default.Bedtime,
                    contentDescription = "Log"
                )
            }
        )
        NavigationBarItem(
            label = { Text("Insights") },
            onClick = onInsightsClick,
            selected =  currentDestination == Insights,
            icon = {
                Icon(
                    imageVector = Icons.Default.Insights,
                    contentDescription = "Insights"
                )
            }
        )
    }
}
