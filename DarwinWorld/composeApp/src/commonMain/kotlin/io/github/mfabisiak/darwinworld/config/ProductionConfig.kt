package io.github.mfabisiak.darwinworld.config

import io.github.mfabisiak.darwinworld.model.Position

data class ProductionConfig(
    override val numberOfPlants: Int = 5,
    override val plantsGrowingEachDay: Int = 3,
    override val numberOfAnimals: Int = 4,
    override val energyConsumedEachDay: Int = 10,
    override val energyRequiredToBreed: Int = 20,
    override val energyGivenToNewborn: Int = 10,
    override val minNumberOfMutations: Int = 1,
    override val maxNumberOfMutations: Int = 3,
    override val genotypeSize: Int = 5,
    override val lowerBound: Position = Position(0, 0),
    override val upperBound: Position = Position(5, 5),
    override val energyFromSinglePlant: Int = 20,
    override val initialEnergy: Int = 100,
    override val energyRequiredToMoveFast: Int = 20,
    override val energyPerExtraStep: Int = 5,
    override val maxRange: Int = 1,
    override val fastAnimalsEnabled: Boolean = false
) : SimulationConfig {

    override val jungle = let {
        val jungleHeight = ((upperBound.y - lowerBound.y) * 0.2).toInt()

        val jungleLowerBound = Position(lowerBound.x, (boundary.height - jungleHeight) / 2)
        val jungleUpperBound = Position(upperBound.x, jungleLowerBound.y + jungleHeight)

        jungleLowerBound..jungleUpperBound
    }
}