package io.github.mfabisiak.darwinworld.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.mfabisiak.darwinworld.logic.Simulation
import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig
import io.github.mfabisiak.darwinworld.statistics.CalculateStatistics
import io.github.mfabisiak.darwinworld.statistics.StatisticsSaver
import kotlinx.coroutines.*

class SimulationViewModel(config: SimulationConfig, private val saver: StatisticsSaver) : ViewModel() {
    val simulation = Simulation(config)

    val simulationState
        get() = simulation.state

    private var simulationJob: Job? = null

    var selectedAnimalId by mutableStateOf<String?>(null)
        private set

    fun toggleAnimalSelection(id: String?) {
        selectedAnimalId = if (selectedAnimalId == id) null else id
    }

    fun previous() = viewModelScope.launch {
        simulation.undo()
    }

    fun next() = viewModelScope.launch {
        simulation.simulateDay()
    }

    fun start() {
        simulationJob = viewModelScope.launch(Dispatchers.Default) {
            while (isActive) {
                simulation.simulateDay()

                val currentStats = CalculateStatistics(simulationState.value)
                saver.saveStats(currentStats)

                delay(500)
            }
        }
    }

    fun stop() {
        simulationJob?.cancel()
    }

}