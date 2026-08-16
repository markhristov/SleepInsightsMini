package com.example.sleepinsightsmini.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "sleep_entries",
    foreignKeys = [
        ForeignKey(
            entity = Predictor::class,
            parentColumns = ["id"],
            childColumns = ["predictorId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Sleep::class,
            parentColumns = ["id"],
            childColumns = ["sleepId"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
)
data class SleepEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sleepId: Long,
    val predictorId: Long,
    val predictorValue: Boolean
)
