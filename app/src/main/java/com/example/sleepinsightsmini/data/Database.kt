package com.example.sleepinsightsmini.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(LocalDateConverter::class)
@Database(
    entities = [
        SleepEntry::class,
        Sleep::class,
        Predictor::class
    ],
    version = 9,
    exportSchema = false,
)
abstract
class Database : RoomDatabase()  {
    abstract fun appDao(): AppDao
}