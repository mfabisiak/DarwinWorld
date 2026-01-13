package io.github.mfabisiak.darwinworld.model.animal

import io.github.mfabisiak.darwinworld.config.AnimalConfig
import io.github.mfabisiak.darwinworld.model.Direction
import io.github.mfabisiak.darwinworld.model.Position

class Animal(
    val config: AnimalConfig,
    var position: Position,
    initialEnergy: Int,
    val genotype: Genotype,
    var direction: Direction
) {

    init {
        if (genotype.genes.size != config.genotypeSize) throw IllegalArgumentException(
            "Animal initialized with " +
                    "genotype of different size than specified in config."
        )
    }

    private val _children = mutableSetOf<Animal>()

    val children
        get() = _children.toSet()

    var energy: Int = initialEnergy
        private set

    val isAlive
        get() = energy > 0

    var age = 0
        private set

    fun endDay() {
        with(config) {
            age += 1
            energy -= energyConsumedEachDay
        }
    }


    fun rotate() {
        val actualRotate = genotype.nextGene()
        direction += actualRotate
    }

    fun breed(parent2: Animal): Animal? {
        val parent1 = this
        with(config) {
            if (!canBreed(parent1) || !canBreed(parent2)) return null

            val child = animalOfParents(parent1, parent2)
            parent1.energy -= energyGivenToNewborn
            parent2.energy -= energyGivenToNewborn

            return child
        }
    }

}

