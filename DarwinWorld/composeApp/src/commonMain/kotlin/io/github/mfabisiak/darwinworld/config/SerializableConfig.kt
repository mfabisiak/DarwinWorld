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