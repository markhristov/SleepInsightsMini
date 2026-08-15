package com.example.sleepinsightsmini.data

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key.Companion.Home
import com.example.sleepinsightsmini.features.log.LogScreen
import kotlinx.serialization.Serializable

@Serializable
data object LogScreen : NavKey

@Composable
fun SleepInsightsMiniApp () {

    val backStack = rememberNavBackStack(LogScreen)

    fun navigateTop(destination: NavKey) {
        backStack.clear()
        backStack.add(Home)
        if (destination != Home) backStack.add(destination)
    }

    fun navigateBack() {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }

}