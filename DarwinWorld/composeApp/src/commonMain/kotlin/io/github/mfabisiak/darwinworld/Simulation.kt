package io.github.mfabisiak.darwinworld

import io.github.mfabisiak.darwinworld.config.SimulationConfig
import io.github.mfabisiak.darwinworld.model.map.WorldMap
import io.github.mfabisiak.darwinworld.model.map.utils.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Simulation(config: SimulationConfig) {

    private val _worldMap: MutableStateFlow<WorldMap> = MutableStateFlow(WorldMap(config))

    private val _day: MutableStateFlow<Int> = MutableStateFlow(0)

    val worldMap
        get() = _worldMap.asStateFlow()

    val day
        get() = _day.asStateFlow()

    init {
        val animalsPositions = config.boundary.random(config.numberOfAnimals)

        val initialMap = _worldMap.value
            .spawnPlants()
            .placeAnimals(animalsPositions)

        updateMapState(initialMap)
    }

    private fun updateMapState(newWorldMap: WorldMap) {
        _worldMap.update { newWorldMap }
    }

    fun simulateDay() {
        val newMap = _worldMap.value
            .removeDead()
            .rotateAnimals()
            .moveAnimals()
            .eatPlants()
            .breedAnimals()
            .spawnPlants()
            .endDay()

        updateMapState(newMap)

        _day.value += 1
    }

}
