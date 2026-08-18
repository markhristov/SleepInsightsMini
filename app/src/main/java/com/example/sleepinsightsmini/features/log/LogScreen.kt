package com.example.sleepinsightsmini.features.log

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.sleepinsightsmini.data.Predictor

@Composable
fun LogScreen(
    predictors: List<Predictor>,
    onCreateNewPredictorPressed: () -> Unit, modifier: Modifier
) {
    val predictorMap = buildMap {
        predictors.forEach {
            put(it.name, false)
        }
    }.toMutableMap()

    Column(modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End) {
        Icon(
            Icons.Default.AddCircle,
            contentDescription = "Add predictor",
            modifier = Modifier.clickable(
                onClick = onCreateNewPredictorPressed
            )
        )

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