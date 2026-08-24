package com.example.sleepinsightsmini.features.log

import android.R.attr.entries
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sleepinsightsmini.SleepInsightsMiniApplication
import com.example.sleepinsightsmini.data.Predictor
import com.example.sleepinsightsmini.data.Repository
import com.example.sleepinsightsmini.data.Sleep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    private var _selectedPredictors: MutableStateFlow<Set<Long>> = MutableStateFlow(setOf())
    val selectedPredictors = _selectedPredictors.asStateFlow()

    private var _currentSleep = MutableStateFlow(Sleep())
    val currentSleep = _currentSleep.asStateFlow()

    fun onSleepChange(duration: Long, quality: Int) {
        _currentSleep.update {
            it.copy(
                duration = duration,
                quality = quality,
            )
        }}

        fun createPredictor(name: String) {
            viewModelScope.launch {
                repository.insertPredictor(name)
            }
        }

        fun onPredictorChecked(
            id: Long,
        ) {
            _selectedPredictors.update { selected ->
                if (id in selected) {
                    selected - id
                } else {
                    selected + id
                }
            }
        }

        fun onPredictorDelete(predictor: Predictor) {
            viewModelScope.launch {

                repository.deletePredictor(predictor)
            }
        }

        fun onSubmitEntries() {
            val selectedIds = _selectedPredictors.value

            val entries =
                uiState.value.predictors.associate {
                    predictor ->
                    predictor.id to (predictor.id in selectedIds)
                }

        viewModelScope.launch {
            repository.insertLog(_currentSleep.value, entries)
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
