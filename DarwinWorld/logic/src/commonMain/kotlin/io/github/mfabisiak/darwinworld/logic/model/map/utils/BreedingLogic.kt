package io.github.mfabisiak.darwinworld.logic.model.map.utils

import io.github.mfabisiak.darwinworld.logic.model.animal.Animal
import io.github.mfabisiak.darwinworld.logic.model.animal.afterBreeding
import io.github.mfabisiak.darwinworld.logic.model.animal.breed
import io.github.mfabisiak.darwinworld.logic.model.map.Animals
import io.github.mfabisiak.darwinworld.logic.model.map.WorldMap

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