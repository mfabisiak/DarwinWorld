package io.github.mfabisiak.darwinworld.model.map.utils

import io.github.mfabisiak.darwinworld.model.Position
import io.github.mfabisiak.darwinworld.model.map.WorldMap
import kotlin.math.min
import kotlin.random.Random

private fun randomPlantPosition(
    availableJunglePositions: MutableSet<Position>,
    availableSteppePositions: MutableSet<Position>
): Position? {
    val inJungle = Random.nextDouble() < 0.8

    val chosenPosition = if (inJungle) {
        availableJunglePositions.randomOrNull() ?: availableSteppePositions.randomOrNull()
    } else {
        availableSteppePositions.randomOrNull() ?: availableJunglePositions.randomOrNull()
    }

    availableJunglePositions.remove(chosenPosition)
    availableSteppePositions.remove(chosenPosition)

    return chosenPosition
}

internal fun WorldMap.spawnPlants(n: Int = config.plantsGrowingEachDay): WorldMap {
    val availableJunglePositions = (config.jungle.toSet() - plants).toMutableSet()
    val availableSteppePositions = (config.boundary.toSet() - config.jungle.toSet() - plants).toMutableSet()

    val plantsToAdd = generateSequence { randomPlantPosition(availableJunglePositions, availableSteppePositions) }
        .take(min(n, availableSteppePositions.size + availableJunglePositions.size))
        .toList()

    return this.copy(plants = plants.addAll(plantsToAdd))
}