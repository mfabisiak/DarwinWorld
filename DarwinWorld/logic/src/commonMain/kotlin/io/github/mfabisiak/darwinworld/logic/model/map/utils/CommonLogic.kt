package io.github.mfabisiak.darwinworld.logic.model.map.utils

import io.github.mfabisiak.darwinworld.logic.model.animal.Animal
import io.github.mfabisiak.darwinworld.logic.model.map.Animals

internal fun Animals.update(newAnimal: Animal): Animals {
    return this.put(newAnimal.id, newAnimal)
}