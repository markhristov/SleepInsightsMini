package com.example.sleepinsightsmini.features.log.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.sleepinsightsmini.data.Predictor

@Composable
fun LogScreen(
    predictors: List<Predictor>,
    checkedPredictors: Set<Long>,
    onSubmit: () -> Unit,
    onCreateNewPredictorPressed: () -> Unit,
    onCheckPredictor: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd) {
            FloatingActionButton(
                onClick = onCreateNewPredictorPressed,
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add predictor",
                )
            }
        }
        if (predictors.isEmpty()) {
            NoPredictorsScreen()
        } else {
            Column(

            ) {
                predictors.forEach { predictor ->
                    PredictorCard(
                        predictor,
                        checked = predictor.id in checkedPredictors,
                        onChecked = onCheckPredictor,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Button(onClick = onSubmit) {
                    Text("Submit")
                }
            }
        }
    }
}

@Composable
fun NoPredictorsScreen() {
    Text("No predictors exist")
}

@Composable
fun PredictorCard(
    predictor: Predictor,
    checked: Boolean,
    onChecked: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = predictor.name)
        Checkbox(
            checked = checked, onCheckedChange = { onChecked(predictor.id) }
        )
    }
}