package com.example.sleepinsightsmini.features.log.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sleepinsightsmini.data.Predictor

@Composable
fun LogScreen(
    predictors: List<Predictor>,
    checkedPredictors: Set<Long>,
    onCheckPredictor: (Long) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (predictors.isEmpty()) {
        NoPredictorsScreen(modifier)
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Column(
                    modifier = Modifier.padding(bottom = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "What affected your day?",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "Select each factor that applies today.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            items(items = predictors, key = { it.id }) { predictor ->
                PredictorCard(
                    predictor = predictor,
                    checked = predictor.id in checkedPredictors,
                    onChecked = onCheckPredictor,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            item {
                Button(
                    onClick = onSubmit,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                ) {
                    Text("Save log")
                }
            }
        }
    }
}

@Composable
fun NoPredictorsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Default.Bedtime,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = "No predictors yet",
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = "Add factors such as exercise, caffeine, or screen time.",
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
fun PredictorCard(
    predictor: Predictor,
    checked: Boolean,
    onChecked: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = { onChecked(predictor.id) },
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (checked) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceContainer
            },
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = predictor.name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )
            Checkbox(
                checked = checked,
                onCheckedChange = { onChecked(predictor.id) },
            )
        }
    }
}
