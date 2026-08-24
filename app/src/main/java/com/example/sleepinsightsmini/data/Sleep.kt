package com.example.sleepinsightsmini.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.ZoneId

const val SECONDS_IN_HOUR: Long = 60 * 60

@Entity(tableName = "sleep")
data class Sleep(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: LocalDate = LocalDate.now(),
    val duration: Long = 8 * SECONDS_IN_HOUR,
    val quality: Int = 4
)
