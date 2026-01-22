package io.github.mfabisiak.darwinworld.model.animal

import io.github.mfabisiak.darwinworld.config.AnimalConfig
import io.github.mfabisiak.darwinworld.model.Direction
import io.github.mfabisiak.darwinworld.model.Position
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Animal(
    val config: AnimalConfig,
    val position: Position,
    val genotype: Genotype = config.randomGenotype(),
    val energy: Int = config.initialEnergy,
    val direction: Direction = Direction.random(),
    val age: Int = 0,
    val id: String = Uuid.random().toString(),
    val childrenIds: PersistentSet<String> = persistentSetOf(),
    val birthDay: Int = 0
) {

    init {
        if (genotype.genes.size != config.genotypeSize) throw IllegalArgumentException(
            "Animal initialized with " +
                    "genotype of different size than specified in config."
        )
    }

    val isAlive
        get() = energy > 0

    val canBreed
        get() = this.energy >= config.energyRequiredToBreed
}

