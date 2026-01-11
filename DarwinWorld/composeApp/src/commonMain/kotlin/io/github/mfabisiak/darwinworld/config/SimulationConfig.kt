package io.github.mfabisiak.darwinworld.config

interface SimulationConfig : AnimalConfig {
    val width: Int
    val height: Int
    val numberOfPlants: Int
    val energyFromSinglePlant: Int
    val plantsGrowingEachDay: Int
    val numberOfAnimals: Int

}
