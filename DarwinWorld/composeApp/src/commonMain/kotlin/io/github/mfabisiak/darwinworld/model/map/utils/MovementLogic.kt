package io.github.mfabisiak.darwinworld.model.map.utils

import io.github.mfabisiak.darwinworld.model.Position
import io.github.mfabisiak.darwinworld.model.animal.Animal
import io.github.mfabisiak.darwinworld.model.animal.rotate
import io.github.mfabisiak.darwinworld.model.map.WorldMap
import io.github.mfabisiak.darwinworld.model.movement
import kotlin.math.max
import kotlin.math.min

private data class MoveResult(
    val animal: Animal,
    val trampledPlants: Set<Position>
)

internal fun WorldMap.moveAnimals(): WorldMap {

    val occupiedPositions = animals.values.map { it.position }.toSet()

    val moveResults = animals.values.map { animal ->
        moveAnimalFast(animal, occupiedPositions)
    }

    val newAnimals = moveResults.fold(animals) { currentAnimals, result ->
        currentAnimals.update(result.animal)
    }

    val allTrampledPlants = moveResults.flatMap { it.trampledPlants }.toSet()
    val newPlants = plants.removeAll(allTrampledPlants)

    return this.copy(animals = newAnimals, plants = newPlants)
}

private fun WorldMap.moveAnimal(animal: Animal): Animal {
    val position = animal.move()
    val boundary = config.boundary

    if (position in boundary) return animal.copy(position = position)

    val newX = boundary.start.x + (position.x - boundary.start.x).mod(boundary.width)

    if (position over boundary) {
        val newPosition = Position(newX, boundary.end.y)
        val newDirection = -animal.direction
        return animal.copy(position = newPosition, direction = newDirection)
    }

    if (position under boundary) {
        val newPosition = Position(newX, boundary.start.y)
        val newDirection = -animal.direction
        return animal.copy(position = newPosition, direction = newDirection)
    }

    val newPosition = Position(newX, position.y)

    return animal.copy(position = newPosition)
}

private fun Animal.move() = position + direction.movement()

private fun WorldMap.moveAnimalFast(
    animal: Animal,
    occupiedPositions: Set<Position>
): MoveResult {

    val extraSteps = (animal.energy - config.energyRequiredToMoveFast) / config.energyPerExtraStep
    val currentRange = min(config.maxRange, 1 + max(0, extraSteps))

    var currentAnimal = animal
    val trampledPlants = mutableSetOf<Position>()
    var collision = false

    for (step in 1..currentRange) {

        val movedAnimal = moveAnimal(currentAnimal)
        val nextPosition = movedAnimal.position

        if (nextPosition in occupiedPositions && nextPosition != animal.position) {
            collision = true
            currentAnimal = movedAnimal
            break
        }

        if (step < currentRange && nextPosition in plants) {
            trampledPlants.add(nextPosition)
        }

        currentAnimal = movedAnimal
    }

    val energyPenalty = if (collision) config.energyConsumedEachDay else 0
    val finalEnergy = max(0, currentAnimal.energy - energyPenalty)

    return MoveResult(currentAnimal.copy(energy = finalEnergy), trampledPlants)
}

internal fun WorldMap.rotateAnimals(): WorldMap {
    val newAnimals = animals.values
        .fold(animals) { currentAnimals, animal ->
            currentAnimals.update(animal.rotate())
        }

    return this.copy(animals = newAnimals)
}