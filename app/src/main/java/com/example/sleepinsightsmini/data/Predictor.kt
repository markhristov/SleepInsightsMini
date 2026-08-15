package com.example.sleepinsightsmini.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Predictor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
)