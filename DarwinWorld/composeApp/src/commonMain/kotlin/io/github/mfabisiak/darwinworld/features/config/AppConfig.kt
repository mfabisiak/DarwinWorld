package io.github.mfabisiak.darwinworld.features.config

import io.github.mfabisiak.darwinworld.config.SimulationConfig
import io.github.mfabisiak.darwinworld.model.Position

data class AppConfig(
    val mapWidth: Int,
    val mapHeight: Int,
    override val numberOfPlants: Int,
    override val plantsGrowingEachDay: Int,
    override val numberOfAnimals: Int,
    override val energyFromSinglePlant: Int,
    override val energyConsumedEachDay: Int,
    override val energyRequiredToBreed: Int,
    override val energyGivenToNewborn: Int,
    override val minNumberOfMutations: Int,
    override val maxNumberOfMutations: Int,
    override val genotypeSize: Int,
) : SimulationConfig {
    override val upperBound = Position(mapWidth,mapHeight)
    override val lowerBound: Position = Position(0,0)
}
