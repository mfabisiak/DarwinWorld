package io.github.mfabisiak.darwinworld.statistics

import io.github.mfabisiak.darwinworld.logic.SimulationState

fun getDayStatistics(simulationState: SimulationState): DayStatistics {

    val worldMap = simulationState.worldMap

    val currentDay = simulationState.day

    val totalAnimals = worldMap.animals.size

    val totalPlants = worldMap.plants.size

    val totalFields = (worldMap.config.upperBound.x + 1) * (worldMap.config.upperBound.y + 1)

    val occupiedByAnimals = worldMap.animals.values.map { it.position }.toSet()
    val occupiedByPlants = worldMap.plants

    val freeAreas = totalFields - (occupiedByAnimals + occupiedByPlants).size

    val averageEnergy = worldMap.animals.values
        .map { it.energy }
        .ifEmpty { listOf(0) }
        .average()

    val averageAgeForDeadAnimals = worldMap.deadAnimals.values
        .map { it.age }
        .ifEmpty { listOf(0) }
        .average()

    val averageNumberOfChildren = worldMap.animals.values
        .map { it.childrenIds.size }
        .ifEmpty { listOf(0) }
        .average()

    val popularGenotypes = worldMap.animals.values
        .groupingBy { it.genotype }
        .eachCount()
        .entries
        .sortedByDescending { it.value }
        .take(3)
        .joinToString(", ") { x ->
            val genesString = x.key.genes.joinToString(" ")
            "[$genesString] (${x.value})"
        }
    val topGenes = worldMap.animals.values
        .groupingBy { it.genotype.genes.actualList }
        .eachCount()
        .maxByOrNull { it.value }
        ?.key

    val topGenotype = worldMap.animals.values
        .map { it.genotype }
        .firstOrNull { it.genes.actualList == topGenes }

    return DayStatistics(
        currentDay,
        totalAnimals,
        totalPlants,
        freeAreas,
        averageEnergy,
        averageAgeForDeadAnimals,
        averageNumberOfChildren,
        popularGenotypes,
        topGenotype
    )
}