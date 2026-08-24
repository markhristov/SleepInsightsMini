package com.example.sleepinsightsmini.features.log.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.sleepinsightsmini.data.Predictor
import com.example.sleepinsightsmini.data.Sleep
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun LogScreen(
    predictors: List<Predictor>,
    checkedPredictors: Set<Long>,
    onCheckPredictor: (Long) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    onPredictorDelete: (Predictor) -> Unit,
    sleep: Sleep,
    onSleepChange: (Long, Int) -> Unit,
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
                    onPredictorDelete = onPredictorDelete
                )
            }

            item {
                SleepCard(
                    sleep = sleep,
                    onSleepChange = onSleepChange
                )
            }

            item {
                Button(
                    onClick = onSubmit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
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
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
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
    onPredictorDelete: (Predictor) -> Unit,
) {
    var menuExpanded by remember {
        mutableStateOf(false)
    }
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = predictor.name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )

            Box {
                IconButton(onClick = { menuExpanded = true }, content = {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Predictor options"
                    )
                })

                DropdownMenu(
                    expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                    DropdownMenuItem(text = { Text("Delete") }, onClick = {
                        onPredictorDelete(predictor)
                    })
                }
            }

            Checkbox(
                checked = checked,
                onCheckedChange = { onChecked(predictor.id) },
            )
        }
    }
}

@Composable
fun SleepCard(
    sleep: Sleep, modifier: Modifier = Modifier, onSleepChange: (Long, Int) -> Unit
) {
    var duration by rememberSaveable {
        mutableStateOf(sleep.duration)
    }
    var quality by rememberSaveable {
        mutableStateOf(sleep.quality)
    }

    Column(modifier = modifier) {
        Text("How was your sleep last night?")

        Text("Time")
        OutlinedTextField(
            value = duration.toDuration(DurationUnit.HOURS).toString(), onValueChange = { onSleepChange(duration, quality) })

        Text("Quality")
        StarRatingPicker(quality, { onSleepChange(duration, it) })
    }

}

@Composable
fun StarRatingPicker(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxStars: Int = 5,
    starSize: Dp = 32.dp,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
) {
    Row(modifier = modifier) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val starColor by animateColorAsState(
                targetValue = if (isSelected) selectedColor else unselectedColor,
                label = "StarColorAnimation"
            )

            Icon(
                imageVector = if (isSelected) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "Rate $i stars",
                tint = starColor,
                modifier = Modifier
                    .size(starSize)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null // Removes the standard rectangular ripple for a cleaner look
                    ) {
                        onRatingChange(i)
                    })
        }
    }
}
