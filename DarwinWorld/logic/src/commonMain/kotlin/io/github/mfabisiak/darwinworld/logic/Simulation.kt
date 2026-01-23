package io.github.mfabisiak.darwinworld.logic

import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig
import io.github.mfabisiak.darwinworld.logic.model.map.WorldMap
import io.github.mfabisiak.darwinworld.logic.model.map.utils.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val MAX_HISTORY_SIZE = 1000
class Simulation(config: SimulationConfig) {

    private val _state: MutableStateFlow<SimulationState> =
        MutableStateFlow(SimulationState(WorldMap(config), 0))

    val state
        get() = _state.asStateFlow()

    private val currentMap
        get() = _state.value.worldMap

    private val currentDay
        get() = _state.value.day

    private val history: ArrayDeque<SimulationState> = ArrayDeque()

    private val poppedHistory: MutableList<SimulationState> = mutableListOf()

    init {
        val animalsPositions = config.boundary.random(config.numberOfAnimals, config.random)

        val initialMap = currentMap
            .spawnPlants(config.numberOfPlants)
            .placeAnimals(animalsPositions)

        updateState(initialMap, 0)
    }

    private fun updateState(newWorldMap: WorldMap, day: Int = currentDay + 1) {
        _state.value = SimulationState(newWorldMap, day)
    }

    fun simulateDay() {
        if (history.size >= MAX_HISTORY_SIZE) history.removeFirst()

        history.add(_state.value)

        if (poppedHistory.isEmpty()) {
            val newMap = currentMap
                .removeDead()
                .rotateAnimals()
                .moveAnimals()
                .eatPlants()
                .breedAnimals()
                .spawnPlants()
                .endDay()
            updateState(newMap)
        } else {
            _state.value = poppedHistory.removeLast()
        }
    }

    fun undo() {
        if (history.isNotEmpty()) {
            poppedHistory.add(_state.value)
            _state.value = history.removeLast()
        }
    }

}

data class SimulationState(val worldMap: WorldMap, val day: Int)