package com.example.sleepinsightsmini.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM predictors ORDER BY name")
    fun observePredictors(): Flow<List<Predictor>>

    @Insert
    suspend fun insertPredictor(predictor: Predictor): Long

}