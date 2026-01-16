package io.github.mfabisiak.darwinworld.config

import io.github.mfabisiak.darwinworld.model.Position

class ProductionConfig(
    override val numberOfPlants: Int,
    override val plantsGrowingEachDay: Int,
    override val numberOfAnimals: Int,
    override val energyConsumedEachDay: Int,
    override val energyRequiredToBreed: Int,
    override val energyGivenToNewborn: Int,
    override val minNumberOfMutations: Int,
    override val maxNumberOfMutations: Int,
    override val genotypeSize: Int,
    override val lowerBound: Position,
    override val upperBound: Position,
    override val energyFromSinglePlant: Int,
    override val initialEnergy: Int
) : SimulationConfig {

    override val jungle = let {
        val jungleHeight = ((upperBound.y - lowerBound.y) * 0.2).toInt()

        val jungleLowerBound = Position(lowerBound.x, jungleHeight - (jungleHeight / 2))
        val jungleUpperBound = Position(upperBound.x, jungleLowerBound.y + jungleHeight)

        jungleLowerBound..jungleUpperBound
    }
}