package com.example.sleepinsightsmini.features.log

import android.R.attr.onClick
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sleepinsightsmini.data.Predictor

@Composable
fun LogScreen(
    predictors: List<Predictor>,

    onCreateNewPredictorPressed: () -> Unit, onSubmit: () -> Unit, modifier: Modifier
) {
    val predictorMap = buildMap {
        predictors.forEach {
            put(it.name, false)
        }
    }.toMutableMap()

    Column(modifier = modifier) {
        OutlinedButton(onClick = onCreateNewPredictorPressed) {
            Icon(
                Icons.Default.AddCircle,
                contentDescription = "Add predictor",
            )
        }

        predictors.forEach { predictor ->
            PredictorCard(
                predictor,
                predictorMap[predictor.name] == true,
                { predictorMap[predictor.name] = it })
        }
    }
}

@Composable
fun PredictorCard(predictor: Predictor, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row {
        Text(text = predictor.name)
        Checkbox(
            checked = checked, onCheckedChange = onCheckedChange
        )
    }
}