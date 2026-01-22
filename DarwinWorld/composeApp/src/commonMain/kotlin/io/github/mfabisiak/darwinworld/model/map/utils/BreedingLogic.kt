package io.github.mfabisiak.darwinworld.model.map.utils

import io.github.mfabisiak.darwinworld.model.animal.Animal
import io.github.mfabisiak.darwinworld.model.animal.afterBreeding
import io.github.mfabisiak.darwinworld.model.animal.breed
import io.github.mfabisiak.darwinworld.model.map.Animals
import io.github.mfabisiak.darwinworld.model.map.WorldMap

internal fun WorldMap.breedAnimals() = this.copy(animals = animals.breed())

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