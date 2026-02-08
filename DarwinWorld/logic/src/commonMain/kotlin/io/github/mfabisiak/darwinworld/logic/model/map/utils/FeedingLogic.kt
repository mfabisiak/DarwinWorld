package io.github.mfabisiak.darwinworld.logic.model.map.utils

import io.github.mfabisiak.darwinworld.logic.model.animal.ANIMAL_COMPARATOR
import io.github.mfabisiak.darwinworld.logic.model.animal.Animal
import io.github.mfabisiak.darwinworld.logic.model.map.WorldMap

internal fun WorldMap.eatPlants(): WorldMap = animals.values
    .groupBy { it.position }
    .filter { it.key in plants }
    .values
    .fold(this) { currentMap, animalsAtPosition ->
        currentMap.eatPlant(animalsAtPosition)
    }

private fun WorldMap.eatPlant(animalsAtPosition: Collection<Animal>): WorldMap {
    if (animalsAtPosition.isEmpty()) return this

    val position = animalsAtPosition.first().position

    if (position !in plants) return this

    val animal = animalsAtPosition
        .shuffled(config.random)
        .maxWith(ANIMAL_COMPARATOR)


    val newAnimals =
        animals.update(
            animal.copy(
                energy = animal.energy + config.energyFromSinglePlant,
                eatenPlants = animal.eatenPlants + 1
            )
        )

    val newPlants = plants.remove(position)

    return this.copy(animals = newAnimals, plants = newPlants)
}