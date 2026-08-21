package com.example.sleepinsightsmini.features.log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sleepinsightsmini.SleepInsightsMiniApplication
import com.example.sleepinsightsmini.data.Predictor
import com.example.sleepinsightsmini.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.Set

data class LogUiState(
    val predictors: List<Predictor> = listOf(),
)

class LogViewModel(
    private val repository: Repository,
) : ViewModel() {
    val uiState: StateFlow<LogUiState> = repository.getPredictors()
        .map { predictors ->
            LogUiState(predictors = predictors)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LogUiState()
        )

    private val _selectedPredictors: MutableStateFlow<Set<Long>> = MutableStateFlow(setOf())
    val selectedPredictors = _selectedPredictors.asStateFlow()

    fun createPredictor(name: String) {
        viewModelScope.launch {
            repository.insertPredictor(name)
        }
    }

    fun onPredictorChecked(
        id: Long,
    ) {
       if (id in selectedPredictors.value) {
           selectedPredictors.value - id
       } else {
           selectedPredictors.value + id
       }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY]
                        as SleepInsightsMiniApplication

                val repository = application.container.repository

                LogViewModel(repository)
            }
        }
    }
}