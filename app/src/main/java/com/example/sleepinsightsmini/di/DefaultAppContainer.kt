package com.example.sleepinsightsmini.di

import android.content.Context
import androidx.room.Room
import com.example.sleepinsightsmini.data.AppDao
import com.example.sleepinsightsmini.data.Database
import com.example.sleepinsightsmini.data.Repository
import com.example.sleepinsightsmini.data.RoomRepository

class DefaultAppContainer(context: Context) : AppContainer {
    private val database = Room.databaseBuilder(
        context.applicationContext,
        Database::class.java,
        "sleep-insights-mini.db",
    ).build()

    private val appDao: AppDao = database.appDao()

    override val repository: Repository = RoomRepository(appDao)


}