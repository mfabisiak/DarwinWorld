package io.github.mfabisiak.darwinworld.statistics

import io.github.mfabisiak.darwinworld.logic.model.map.WorldMap

fun CalculateStatistics(worldMap: WorldMap): SimulationStatistics {

    val totalAnimals = worldMap.animals.size

    val totalPlants = worldMap.plants.size

    val totalFields = (worldMap.config.upperBound.x + 1) * (worldMap.config.upperBound.y + 1)

    val occupiedByAnimals = worldMap.animals.values.map { it.position }.toSet()
    val occupiedByPlants = worldMap.plants

    val freeAreas = totalFields - (occupiedByAnimals + occupiedByPlants).size

    val averageEnergy = worldMap.animals.values
        .map { it.energy }
        .average()

    val averageAgeForDeadAnimals = worldMap.deadAnimals.values
        .map { it.age }
        .average()

    val averageNumberOfChildren = worldMap.animals.values
        .map { it.childrenIds.size }
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

    return SimulationStatistics(
        totalAnimals,
        totalPlants,
        freeAreas,
        averageEnergy,
        averageAgeForDeadAnimals,
        averageNumberOfChildren,
        popularGenotypes
    )
}