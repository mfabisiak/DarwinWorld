package io.github.mfabisiak.darwinworld

import io.github.mfabisiak.darwinworld.config.SimulationConfig
import io.github.mfabisiak.darwinworld.model.animal.Animal
import io.github.mfabisiak.darwinworld.model.map.WorldMap
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

        val mapWithAnimals = (0..<config.numberOfAnimals).fold(mapWithPlants) { currentMap, _ ->
            currentMap.addAnimal(Animal(config, config.boundary.random()))
        }

        updateState(mapWithAnimals)
    }

    private fun updateState(newWorldMap: WorldMap) {
        _worldMap.update { newWorldMap }
    }


}