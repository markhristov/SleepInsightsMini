package com.example.sleepinsightsmini

import android.app.Application
import com.example.sleepinsightsmini.di.AppContainer
import com.example.sleepinsightsmini.di.DefaultAppContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SleepInsightsMiniApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContWainer(this)
        applicationScope.launch {
            container.initializeData()
        }
    }
}
}