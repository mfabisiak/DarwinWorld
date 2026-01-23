package io.github.mfabisiak.darwinworld.config

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig
import io.github.mfabisiak.darwinworld.logic.model.Position
import kotlin.random.Random

class ConfigBuilder {
    var numberOfPlants by mutableStateOf(5)
    var plantsGrowingEachDay by mutableStateOf(3)
    var numberOfAnimals by mutableStateOf(4)
    var energyConsumedEachDay by mutableStateOf(10)
    var energyRequiredToBreed by mutableStateOf(20)
    var energyGivenToNewborn by mutableStateOf(10)
    var minNumberOfMutations by mutableStateOf(1)
    var maxNumberOfMutations by mutableStateOf(3)
    var genotypeSize by mutableStateOf(5)
    var mapWidth by mutableStateOf(10)
    var mapHeight by mutableStateOf(10)
    var energyFromSinglePlant by mutableStateOf(20)
    var initialEnergy by mutableStateOf(80)
    var randomSeed by mutableStateOf(Random.nextInt(5000))
    var energyRequiredToMoveFast by mutableStateOf(20)
    var energyPerExtraStep by mutableStateOf(5)
    var maxRange by mutableStateOf(1)
    var fastAnimalsEnabled by mutableStateOf(false)


    fun build(): SimulationConfig = ProductionConfig(
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

    fun toSerializableConfig() = SerializableConfig(
        numberOfPlants = numberOfPlants,
        plantsGrowingEachDay = plantsGrowingEachDay,
        numberOfAnimals = numberOfAnimals,
        energyConsumedEachDay = energyConsumedEachDay,
        energyRequiredToBreed = energyRequiredToBreed,
        energyGivenToNewborn = energyGivenToNewborn,
        minNumberOfMutations = minNumberOfMutations,
        maxNumberOfMutations = maxNumberOfMutations,
        genotypeSize = genotypeSize,
        mapWidth = mapWidth,
        mapHeight = mapHeight,
        energyFromSinglePlant = energyFromSinglePlant,
        initialEnergy = initialEnergy,
        randomSeed = randomSeed,
        energyRequiredToMoveFast = energyRequiredToMoveFast,
        energyPerExtraStep = energyPerExtraStep,
        maxRange = maxRange,
        fastAnimalsEnabled = fastAnimalsEnabled
    )

    fun fromSerializableConfig(config: SerializableConfig) {
        numberOfPlants = config.numberOfPlants
        plantsGrowingEachDay = config.plantsGrowingEachDay
        numberOfAnimals = config.numberOfAnimals
        energyConsumedEachDay = config.energyConsumedEachDay
        energyRequiredToBreed = config.energyRequiredToBreed
        energyGivenToNewborn = config.energyGivenToNewborn
        minNumberOfMutations = config.minNumberOfMutations
        maxNumberOfMutations = config.maxNumberOfMutations
        genotypeSize = config.genotypeSize
        mapWidth = config.mapWidth
        mapHeight = config.mapHeight
        energyFromSinglePlant = config.energyFromSinglePlant
        initialEnergy = config.initialEnergy
        randomSeed = config.randomSeed
        energyRequiredToMoveFast = config.energyRequiredToMoveFast
        energyPerExtraStep = config.energyPerExtraStep
        maxRange = config.maxRange
        fastAnimalsEnabled = config.fastAnimalsEnabled
    }

}
