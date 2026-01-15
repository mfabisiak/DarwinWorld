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
        val mapWithPlants = (0..<config.numberOfPlants).fold(_worldMap.value) { currentMap, _ ->
            currentMap.addPlant(config.randomPlantPosition(currentMap.plants))
        }

        val animalsPositions = config.boundary.random(config.numberOfAnimals)

        val mapWithAnimals = mapWithPlants.placeAnimals(animalsPositions)

        updateMapState(mapWithAnimals)
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
            .endDay()

        updateMapState(newMap)
    }

}
