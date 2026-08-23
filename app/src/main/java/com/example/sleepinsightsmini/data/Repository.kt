package com.example.sleepinsightsmini.data

import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun insertPredictor(name: String)

    fun getPredictors(): Flow<List<Predictor>>

    suspend fun insertSleepEntries(entries: List<SleepEntry>)
}