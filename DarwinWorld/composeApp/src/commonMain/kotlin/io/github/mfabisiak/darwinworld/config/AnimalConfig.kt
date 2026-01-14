package io.github.mfabisiak.darwinworld.config

interface AnimalConfig {
    val initialEnergy: Int
    val energyConsumedEachDay: Int
    val energyRequiredToBreed: Int
    val energyGivenToNewborn: Int
    val minNumberOfMutations: Int
    val maxNumberOfMutations: Int
    val genotypeSize: Int
}