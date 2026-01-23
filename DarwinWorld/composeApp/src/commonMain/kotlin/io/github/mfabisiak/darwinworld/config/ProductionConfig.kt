package io.github.mfabisiak.darwinworld.config

import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig
import io.github.mfabisiak.darwinworld.logic.model.Position
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProductionConfig(
    override val numberOfPlants: Int = 5,
    override val plantsGrowingEachDay: Int = 3,
    override val numberOfAnimals: Int = 4,
    override val energyConsumedEachDay: Int = 10,
    override val energyRequiredToBreed: Int = 20,
    override val energyGivenToNewborn: Int = 10,
    override val minNumberOfMutations: Int = 1,
    override val maxNumberOfMutations: Int = 3,
    override val genotypeSize: Int = 5,
    override val lowerBound: Position = Position(
        0,
        0
    ),
    override val upperBound: Position = Position(
        5,
        5
    ),
    override val energyFromSinglePlant: Int = 20,
    override val initialEnergy: Int = 100,
    randomSeed: Int = 1,
    energyRequiredToMoveFast: Int = 20,
    energyPerExtraStep: Int = 5,
    maxRange: Int = 1,
    fastAnimalsEnabled: Boolean = false,

) : SimulationConfig {

    override val jungle = let {
        val jungleHeight = ((upperBound.y - lowerBound.y) * 0.2).toInt()

        val jungleLowerBound = Position(
            lowerBound.x,
            (boundary.height - jungleHeight) / 2
        )
        val jungleUpperBound = Position(
            upperBound.x,
            jungleLowerBound.y + jungleHeight
        )

        jungleLowerBound..jungleUpperBound
    }

    override val random = Random(randomSeed)


    override val energyRequiredToMoveFast = if (fastAnimalsEnabled) energyRequiredToMoveFast else 0

    override val energyPerExtraStep = if (fastAnimalsEnabled) energyPerExtraStep else 0

    override val maxRange = if (fastAnimalsEnabled) maxRange else 1

    @OptIn(ExperimentalUuidApi::class)
    override val id = Uuid.random().toString()

}
