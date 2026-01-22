package io.github.mfabisiak.darwinworld.logic

import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig
import io.github.mfabisiak.darwinworld.logic.model.map.WorldMap
import io.github.mfabisiak.darwinworld.logic.model.map.utils.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Simulation(config: SimulationConfig) {

    private val _state: MutableStateFlow<SimulationState> =
        MutableStateFlow(SimulationState(WorldMap(config), 0))

    val state
        get() = _state.asStateFlow()

    private val currentMap
        get() = _state.value.worldMap

    private val currentDay
        get() = _state.value.day

    init {
        val animalsPositions = config.boundary.random(config.numberOfAnimals)

        val initialMap = currentMap
            .spawnPlants(config.numberOfPlants)
            .placeAnimals(animalsPositions)

        updateState(initialMap, 0)
    }

    private fun updateState(newWorldMap: WorldMap, day: Int = currentDay + 1) {
        _state.value = SimulationState(newWorldMap, day)
    }

    fun simulateDay() {
        val newMap = currentMap
            .removeDead()
            .rotateAnimals()
            .moveAnimals()
            .eatPlants()
            .breedAnimals()
            .spawnPlants()
            .endDay()

        updateState(newMap)
    }

}

data class SimulationState(val worldMap: WorldMap, val day: Int)