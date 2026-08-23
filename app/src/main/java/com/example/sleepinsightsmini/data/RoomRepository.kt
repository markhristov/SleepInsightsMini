package com.example.sleepinsightsmini.data

import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val dao: AppDao) : Repository {
    override suspend fun insertPredictor(name: String) {
        dao.insertPredictor(Predictor(name = name))
    }

    override fun getPredictors(): Flow<List<Predictor>> {
        return dao.observePredictors()
    }

    override suspend fun insertSleepEntries(entries: List<SleepEntry>) {
        dao.insertSleepEntries(entries)
    }
}