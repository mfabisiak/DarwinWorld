package io.github.mfabisiak.darwinworld

import io.github.mfabisiak.darwinworld.config.SimulationConfig
import io.github.mfabisiak.darwinworld.model.map.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Simulation(config: SimulationConfig) {

    private val _worldMap: MutableStateFlow<WorldMap> = MutableStateFlow(WorldMap(config))

    val worldMap
        get() = _worldMap.asStateFlow()

    init {
        val animalsPositions = config.boundary.random(config.numberOfAnimals)

        val initialMap = _worldMap.value
            .spawnPlants(config.numberOfPlants)
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
    }

}
