package com.example.sleepinsightsmini.features.log

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sleepinsightsmini.data.Predictor

@Composable
fun LogScreen(
    uiState: List<Predictor>,
    onCreateNewPredictor: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        AddPredictorButton(onCreateNewPredictor)
//        items(uiState) {
//
//        }
    }
}

@Composable
fun AddPredictorButton(onCreateNewPredictor: (String) -> Unit) {

}