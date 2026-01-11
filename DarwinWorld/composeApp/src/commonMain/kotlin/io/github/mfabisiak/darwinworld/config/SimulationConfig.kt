package io.github.mfabisiak.darwinworld.config

import io.github.mfabisiak.darwinworld.utils.AnimalConfig

interface SimulationConfig : AnimalConfig {
    val width: Int
    val height: Int
    val numberOfPlants: Int
    val energyFromSinglePlant: Int
    val plantsGrowingEachDay: Int
    val numberOfAnimals: Int

}
