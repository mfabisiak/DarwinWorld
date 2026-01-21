package io.github.mfabisiak.darwinworld.model.map

import io.github.mfabisiak.darwinworld.model.Position
import io.github.mfabisiak.darwinworld.model.animal.*
import io.github.mfabisiak.darwinworld.model.movement
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

data class MoveResult(
    val animal: Animal,
    val trampledPlants: Set<Position>
)

fun Animals.update(newAnimal: Animal): Animals {
    return this.put(newAnimal.id, newAnimal)
}

private fun WorldMap.eatPlant(animalsAtPosition: Collection<Animal>): WorldMap {
    if (animalsAtPosition.isEmpty()) return this

    val position = animalsAtPosition.first().position

    if (position !in plants) return this

    val animal = animalsAtPosition
        .shuffled()
        .maxWith(compareBy<Animal> { it.energy }
            .thenBy { it.age }
            .thenBy { it.childrenIds.size })


    val newAnimals = animals.update(animal.copy(energy = animal.energy + config.energyFromSinglePlant))

    val newPlants = plants.remove(position)

    return this.copy(animals = newAnimals, plants = newPlants)
}

fun WorldMap.eatPlants(): WorldMap = animals.values
    .groupBy { it.position }
    .filter { it.key in plants }
    .values
    .fold(this) { currentMap, animalsAtPosition ->
        currentMap.eatPlant(animalsAtPosition)
    }

fun WorldMap.rotateAnimals(): WorldMap {
    val newAnimals = animals.values
        .fold(animals) { currentAnimals, animal ->
            currentAnimals.update(animal.rotate())
        }

    return this.copy(animals = newAnimals)
}

fun WorldMap.moveAnimals(): WorldMap {

    val occupiedPositions = if (config.fastAnimalsEnabled) {
        animals.values.map { it.position }.toSet()
    } else {
        emptySet()
    }

    val moveResults = animals.values.map { animal ->
        if (config.fastAnimalsEnabled) {
            moveAnimalFast(animal, occupiedPositions)
        } else {
            MoveResult(moveAnimal(animal), emptySet())
        }
    }

    val newAnimals = moveResults.fold(animals) { currentAnimals, result ->
        currentAnimals.update(result.animal)
    }

    val allTrampledPlants = moveResults.flatMap { it.trampledPlants }.toSet()
    val newPlants = plants.removeAll(allTrampledPlants)

    return this.copy(animals = newAnimals, plants = newPlants)
}

fun WorldMap.removeDead(): WorldMap {
    val (newAnimals, newDeadAnimals) = animals.values
        .filter { !it.isAlive }
        .fold(animals to deadAnimals) { (currentAnimals, currentDeadAnimals), animal ->
            currentAnimals.remove(animal.id) to currentDeadAnimals.update(animal)
        }

    if (newDeadAnimals.size == deadAnimals.size) return this

    return this.copy(animals = newAnimals, deadAnimals = newDeadAnimals)
}

fun WorldMap.placeAnimals(positions: Collection<Position>): WorldMap {
    val newAnimals = positions
        .fold(animals) { currentAnimals, position ->
            currentAnimals.update(Animal(config, position))
        }

    return this.copy(animals = newAnimals)
}

private fun Animals.breed() = this.values
    .filter { it.canBreed }
    .groupBy { it.position }
    .values
    .filter { it.size >= 2 }
    .map { animalsAtPosition ->
        animalsAtPosition.shuffled()
            .sortedWith(compareBy<Animal> { it.energy }
                .thenBy { it.age }
                .thenBy { it.childrenIds.size })
            .takeLast(2)
    }
    .fold(this) { currentAnimals, (parent1, parent2) ->
        currentAnimals
            .update(parent1.breed(parent2))
            .update(parent1.afterBreeding())
            .update(parent2.afterBreeding())
    }

fun WorldMap.breedAnimals() = this.copy(animals = animals.breed())

private fun Animals.endDay() = this.values
    .fold(this) { currentAnimals, animal ->
        currentAnimals.update(animal.afterDay())
    }

fun WorldMap.endDay() = this.copy(animals = animals.endDay())

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

fun WorldMap.spawnPlants(n: Int = config.plantsGrowingEachDay): WorldMap {
    val availableJunglePositions = (config.jungle.toSet() - plants).toMutableSet()
    val availableSteppePositions = (config.boundary.toSet() - config.jungle.toSet() - plants).toMutableSet()

    val plantsToAdd = generateSequence { randomPlantPosition(availableJunglePositions, availableSteppePositions) }
        .take(min(n, availableSteppePositions.size + availableJunglePositions.size))
        .toList()

    return this.copy(plants = plants.addAll(plantsToAdd))
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