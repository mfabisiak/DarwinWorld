package io.github.mfabisiak.darwinworld.config

interface SimulationConfig : AnimalConfig, MapConfig {
    val numberOfPlants: Int
    val plantsGrowingEachDay: Int
    val numberOfAnimals: Int

}
