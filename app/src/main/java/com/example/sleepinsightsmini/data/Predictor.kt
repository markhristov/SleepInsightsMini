package com.example.sleepinsightsmini.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "predictors")
data class Predictor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
)