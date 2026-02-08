package io.github.mfabisiak.darwinworld.logic.model.map.utils

import io.github.mfabisiak.darwinworld.logic.model.animal.ANIMAL_COMPARATOR
import io.github.mfabisiak.darwinworld.logic.model.animal.afterBreeding
import io.github.mfabisiak.darwinworld.logic.model.animal.breed
import io.github.mfabisiak.darwinworld.logic.model.map.Animals
import io.github.mfabisiak.darwinworld.logic.model.map.WorldMap
import kotlin.random.Random

internal fun WorldMap.breedAnimals() = this.copy(animals = animals.breed(config.random))

private fun Animals.breed(random: Random = Random) = this.values
    .filter { it.canBreed }
    .groupBy { it.position }
    .values
    .filter { it.size >= 2 }
    .map { animalsAtPosition ->
        animalsAtPosition.shuffled(random)
            .sortedWith(ANIMAL_COMPARATOR)
            .takeLast(2)
    }
    .fold(this) { currentAnimals, (parent1, parent2) ->
        val child = parent1.breed(parent2)
        currentAnimals
            .update(child)
            .update(parent1.afterBreeding(child.id))
            .update(parent2.afterBreeding(child.id))
    }