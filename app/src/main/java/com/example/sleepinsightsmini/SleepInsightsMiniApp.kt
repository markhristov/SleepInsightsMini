package com.example.sleepinsightsmini

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.sleepinsightsmini.features.log.LogScreen
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

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(name = backStack.)
        },
        bottomBar = {
            BottomNavBar(
                onLogClick = { navigateTop(Log) },
                onInsightsClick = { navigateTop(Insights) },
                modifier = Modifier.padding()
            )
        }, modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack, onBack = ::navigateBack, entryProvider = entryProvider {
                entry<Log> {
                    LogScreen(
                        predictors = logUiState.predictors,
                        onCreateNewPredictorPressed = { navigateTop(CreatePredictor) },
                        modifier = Modifier.padding(innerPadding)
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
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    name: String
) {
    CenterAlignedTopAppBar(title = {Text(text = name)})
}

@Composable
fun BottomNavBar(
    onLogClick: () -> Unit, onInsightsClick: () -> Unit, modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        NavigationBarItem(
            label = { Text("Log Screen") },
            onClick = onLogClick,
            selected = true,
            icon = Icon(Icons.Default.AddCircle)
        )
        RouteCard("Insights Screen", onClick = onInsightsClick)
    }
}

@Composable
fun RouteCard(name: String, onClick: () -> Unit) {
    Text(text = name)
}