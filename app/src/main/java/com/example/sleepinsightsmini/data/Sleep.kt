package com.example.sleepinsightsmini.data

import java.time.LocalDate

data class Sleep(
    val id: Int,
    val data: LocalDate,
    val duration: Long,
    val quality: Int
)
