package io.github.mfabisiak.darwinworld.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.mfabisiak.darwinworld.logic.Simulation
import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig
import kotlinx.coroutines.*

class SimulationViewModel(config: SimulationConfig) : ViewModel() {
    val simulation = Simulation(config)

    val simulationState
        get() = simulation.state

    private var simulationJob: Job? = null

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
                delay(500)
            }
        }
    }

    fun stop() {
        simulationJob?.cancel()
    }

}