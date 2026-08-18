package com.example.sleepinsightsmini

import android.app.Application
import com.example.sleepinsightsmini.di.AppContainer
import com.example.sleepinsightsmini.di.DefaultAppContainer

class SleepInsightsMiniApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}
