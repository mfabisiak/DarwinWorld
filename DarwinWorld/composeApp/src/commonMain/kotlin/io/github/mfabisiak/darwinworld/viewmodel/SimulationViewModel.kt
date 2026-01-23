package io.github.mfabisiak.darwinworld.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.mfabisiak.darwinworld.logic.Simulation
import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig
import io.github.mfabisiak.darwinworld.statistics.DayStatistics
import io.github.mfabisiak.darwinworld.statistics.getDayStatistics
import kotlinx.coroutines.*

class SimulationViewModel(config: SimulationConfig) : ViewModel() {
    val simulation = Simulation(config)

    val simulationState
        get() = simulation.state


    private var simulationJob: Job? = null

    private val statisticsHistory = mutableListOf(getDayStatistics(simulationState.value))

    private fun nextDay() {
        simulation.simulateDay()
        statisticsHistory.add(getDayStatistics(simulationState.value))
    }

    fun previous() = viewModelScope.launch {
        simulation.undo()
        statisticsHistory.removeLast()
    }

    fun next() = viewModelScope.launch {
        nextDay()
    }

    fun start() {
        simulationJob = viewModelScope.launch(Dispatchers.Default) {
            while (isActive) {
                nextDay()

                delay(500)
            }
        }
    }

    fun stop() {
        simulationJob?.cancel()
    }

    fun getStatisticsCsv() = (listOf(DayStatistics.CSV_HEADER) + statisticsHistory.map { it.toCsvRow() })
        .fold(StringBuilder()) { builder, row -> builder.appendLine(row) }
        .toString()

}