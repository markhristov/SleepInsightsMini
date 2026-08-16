package com.example.sleepinsightsmini.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "sleep")
data class Sleep(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: LocalDate,
    val duration: Long,
    val quality: Int
)
