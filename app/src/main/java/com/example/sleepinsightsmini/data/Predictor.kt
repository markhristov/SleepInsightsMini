package com.example.sleepinsightsmini.data

enum class PredictorType {
    BOOLEAN, NUMBER,
}

data class Predictor(
    val id: Int,
    val name: String,
    val type: PredictorType,
)