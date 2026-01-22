package io.github.mfabisiak.darwinworld.model.map.utils

import io.github.mfabisiak.darwinworld.model.animal.Animal
import io.github.mfabisiak.darwinworld.model.map.WorldMap

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
        .shuffled()
        .maxWith(compareBy<Animal> { it.energy }
            .thenBy { it.age }
            .thenBy { it.childrenIds.size })


    val newAnimals = animals.update(animal.copy(energy = animal.energy + config.energyFromSinglePlant))

    val newPlants = plants.remove(position)

    return this.copy(animals = newAnimals, plants = newPlants)
}