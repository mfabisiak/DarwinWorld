package io.github.mfabisiak.darwinworld.config

import io.github.mfabisiak.darwinworld.logic.model.Position
import kotlinx.serialization.Serializable

@Serializable
data class SerializableConfig(
    val numberOfPlants: Int,
    val plantsGrowingEachDay: Int,
    val numberOfAnimals: Int,
    val energyConsumedEachDay: Int,
    val energyRequiredToBreed: Int,
    val energyGivenToNewborn: Int,
    val minNumberOfMutations: Int,
    val maxNumberOfMutations: Int,
    val genotypeSize: Int,
    val mapWidth: Int,
    val mapHeight: Int,
    val energyFromSinglePlant: Int,
    val initialEnergy: Int,
    val randomSeed: Int,
    val energyRequiredToMoveFast: Int,
    val energyPerExtraStep: Int,
    val maxRange: Int,
    val fastAnimalsEnabled: Boolean
) {
    fun toSimulationConfig() = ProductionConfig(
        numberOfPlants,
        plantsGrowingEachDay,
        numberOfAnimals,
        energyConsumedEachDay,
        energyRequiredToBreed,
        energyGivenToNewborn,
        minNumberOfMutations,
        maxNumberOfMutations,
        genotypeSize,
        Position(0, 0),
        Position(mapWidth - 1, mapHeight - 1),
        energyFromSinglePlant,
        initialEnergy,
        randomSeed,
        energyRequiredToMoveFast,
        energyPerExtraStep,
        maxRange,
        fastAnimalsEnabled
    )
}

val CLASSIC_CONFIG = SerializableConfig(
    mapWidth = 20,
    mapHeight = 20,
    numberOfPlants = 40,
    plantsGrowingEachDay = 5,
    energyFromSinglePlant = 20,

    numberOfAnimals = 15,
    initialEnergy = 50,
    energyConsumedEachDay = 1,

    energyRequiredToBreed = 30,
    energyGivenToNewborn = 15,

    genotypeSize = 8,
    minNumberOfMutations = 0,
    maxNumberOfMutations = 2,

    fastAnimalsEnabled = false,
    energyRequiredToMoveFast = 5,
    energyPerExtraStep = 5,
    maxRange = 1,

    randomSeed = 101
)

val DESERT_CONFIG = SerializableConfig(
    mapWidth = 50,
    mapHeight = 50,
    numberOfPlants = 10,
    plantsGrowingEachDay = 1,
    energyFromSinglePlant = 100,

    numberOfAnimals = 30,
    initialEnergy = 150,
    energyConsumedEachDay = 2,

    energyRequiredToBreed = 200,
    energyGivenToNewborn = 80,

    genotypeSize = 10,
    minNumberOfMutations = 0,
    maxNumberOfMutations = 1,

    fastAnimalsEnabled = true,
    energyRequiredToMoveFast = 10,
    energyPerExtraStep = 5,
    maxRange = 3,

    randomSeed = 202
)

val FULL_OF_PLANTS_CONFIG = SerializableConfig(
    mapWidth = 15,
    mapHeight = 15,
    numberOfPlants = 100,
    plantsGrowingEachDay = 30,
    energyFromSinglePlant = 10,

    numberOfAnimals = 50,
    initialEnergy = 30,
    energyConsumedEachDay = 1,

    energyRequiredToBreed = 15,
    energyGivenToNewborn = 8,

    genotypeSize = 5,
    minNumberOfMutations = 2,
    maxNumberOfMutations = 6,

    fastAnimalsEnabled = false,
    energyRequiredToMoveFast = 0,
    energyPerExtraStep = 0,
    maxRange = 1,

    randomSeed = 303
)

val FAST_ANIMALS_CONFIG = SerializableConfig(
    mapWidth = 30,
    mapHeight = 30,
    numberOfPlants = 40,
    plantsGrowingEachDay = 8,
    energyFromSinglePlant = 25,

    numberOfAnimals = 20,
    initialEnergy = 70,
    energyConsumedEachDay = 1,

    energyRequiredToBreed = 50,
    energyGivenToNewborn = 25,

    genotypeSize = 20,
    minNumberOfMutations = 5,
    maxNumberOfMutations = 15,

    fastAnimalsEnabled = true,
    energyRequiredToMoveFast = 5,
    energyPerExtraStep = 2,
    maxRange = 5,

    randomSeed = 404
)