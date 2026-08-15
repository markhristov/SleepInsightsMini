package com.example.sleepinsightsmini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.sleepinsightsmini.ui.theme.SleepInsightsMiniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SleepInsightsMiniTheme {
                SleepInsightsMiniApp()
            }
        }
    }
}
