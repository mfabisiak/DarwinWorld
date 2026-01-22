package io.github.mfabisiak.darwinworld.model.map.utils

import io.github.mfabisiak.darwinworld.model.animal.Animal
import io.github.mfabisiak.darwinworld.model.map.Animals

internal fun Animals.update(newAnimal: Animal): Animals {
    return this.put(newAnimal.id, newAnimal)
}