package com.example.sleepinsightsmini.data

import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun insertPredictor(name: String)

    fun getPredictors(): Flow<List<Predictor>>

    suspend fun deletePredictor(predictor: Predictor)

    suspend fun insertLog(sleep: Sleep, predictors: Map<Long, Boolean>)
}