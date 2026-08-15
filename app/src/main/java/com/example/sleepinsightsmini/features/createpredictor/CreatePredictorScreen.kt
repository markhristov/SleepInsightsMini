package com.example.sleepinsightsmini.features.createpredictor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CreatePredictorScreen(onSubmit: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        val name = rememberTextFieldState(initialText = "Hello")
        TextField(
            state = name,
            label = { Text("Predictor Name") }
        )

        Button(onClick = { onSubmit(name.text.toString()) }) {
            Text(text = "Submit")
        }
    }
}

@Preview
@Composable
fun CreatePredictorScreenPreview() {
    CreatePredictorScreen({})
}