package com.example.sleepinsightsmini

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.sleepinsightsmini.features.createpredictor.CreatePredictorScreen
import com.example.sleepinsightsmini.features.log.LogScreen
import kotlinx.serialization.Serializable

@Serializable
data object Log : NavKey

@Serializable
data object CreatePredictor : NavKey

@Serializable
data object Insights : NavKey

@Composable
fun SleepInsightsMiniApp() {

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

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }, bottomBar = {
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
                        listOf(),
                        { navigateTop(CreatePredictor) },
                        {},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                entry<CreatePredictor> {
                    CreatePredictorScreen(
                        {}, modifier = Modifier.padding(innerPadding)
                    )
                }
            })
    }
}


@Composable
fun BottomNavBar(
    onLogClick: () -> Unit, onInsightsClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.Center
    ) {
        RouteCard("Log Screen", onClick = onLogClick)
        RouteCard("Insights Screen", onClick = onInsightsClick)
    }
}

@Composable
fun RouteCard(name: String, onClick: () -> Unit) {
    Text(text = name)
}