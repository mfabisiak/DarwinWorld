package io.github.mfabisiak.darwinworld.logic.model.map.utils

import io.github.mfabisiak.darwinworld.logic.model.Position
import io.github.mfabisiak.darwinworld.logic.model.animal.Animal
import io.github.mfabisiak.darwinworld.logic.model.animal.afterDay
import io.github.mfabisiak.darwinworld.logic.model.map.Animals
import io.github.mfabisiak.darwinworld.logic.model.map.WorldMap


private fun Animals.endDay() = this.values
    .fold(this) { currentAnimals, animal ->
        currentAnimals.update(animal.afterDay())
    }

internal fun WorldMap.endDay() = this.copy(animals = animals.endDay())

internal fun WorldMap.removeDead(): WorldMap {
    val (newAnimals, newDeadAnimals) = animals.values
        .filter { !it.isAlive }
        .fold(animals to deadAnimals) { (currentAnimals, currentDeadAnimals), animal ->
            currentAnimals.remove(animal.id) to currentDeadAnimals.update(animal)
        }

    if (newDeadAnimals.size == deadAnimals.size) return this

    return this.copy(animals = newAnimals, deadAnimals = newDeadAnimals)
}

internal fun WorldMap.placeAnimals(positions: Collection<Position>): WorldMap {
    val newAnimals = positions
        .fold(animals) { currentAnimals, position ->
            currentAnimals.update(Animal(config, position))
        }

    return this.copy(animals = newAnimals)
}