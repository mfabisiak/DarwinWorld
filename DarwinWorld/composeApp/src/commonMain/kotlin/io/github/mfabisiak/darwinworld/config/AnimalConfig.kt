package io.github.mfabisiak.darwinworld.config

import io.github.mfabisiak.darwinworld.model.animal.Animal

interface AnimalConfig {
    val energyConsumedEachDay: Int
    val energyRequiredToBreed: Int
    val energyGivenToNewborn: Int
    val minNumberOfMutations: Int
    val maxNumberOfMutations: Int
    val genotypeSize: Int

    fun Animal.canBreed(animal: Animal) = animal.energy >= energyRequiredToBreed

}