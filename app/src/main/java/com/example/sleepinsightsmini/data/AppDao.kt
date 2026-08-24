package com.example.sleepinsightsmini.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM predictors ORDER BY name")
    fun observePredictors(): Flow<List<Predictor>>

    @Insert
    suspend fun insertPredictor(predictor: Predictor)

    @Insert
    suspend fun insertSleepEntries(entries: List<SleepEntry>)

    @Delete
    suspend fun deletePredictor(predictor: Predictor)

    @Insert
    suspend fun insertSleep(sleep: Sleep): Long

    @Transaction
    suspend fun insertLog(
        sleep: Sleep,
        predictorValues: Map<Long, Boolean>,
    ) {
        val sleepId = insertSleep(sleep)

        val entries = predictorValues.map { (predictorId, value) ->
            SleepEntry(
                sleepId = sleepId,
                predictorId = predictorId,
                predictorValue = value,
            )
        }

        insertSleepEntries(entries)
    }
}