package io.github.mfabisiak.darwinworld.logic.config

import kotlin.random.Random

interface AnimalConfig {
    val initialEnergy: Int
    val energyConsumedEachDay: Int
    val energyRequiredToBreed: Int
    val energyGivenToNewborn: Int
    val minNumberOfMutations: Int
    val maxNumberOfMutations: Int
    val genotypeSize: Int
    val random: Random
}