package io.github.mfabisiak.darwinworld.model.animal

import io.github.mfabisiak.darwinworld.config.AnimalConfig
import kotlin.random.Random


fun AnimalConfig.genotypeOfParents(parent1: Animal, parent2: Animal): Genotype {

    val (leftParent, rightParent) = setOf(parent1, parent2).shuffled()

    val divisionIndex = genotypeSize * leftParent.energy / (leftParent.energy + rightParent.energy)

    val mutatingGenes = (0..<genotypeSize).shuffled()
        .take(Random.nextInt(minNumberOfMutations, maxNumberOfMutations + 1))

    val genesList = listOf(
        leftParent.genotype.genes.take(divisionIndex),
        rightParent.genotype.genes.takeLast(genotypeSize - divisionIndex)
    ).flatten().mapIndexed { index, gene ->
        if (index in mutatingGenes) GENE_RANGE.random() else gene
    }

    return Genotype(genesList)
}

fun AnimalConfig.randomGenotype() = Genotype(generateSequence { GENE_RANGE.random() }
    .take(genotypeSize).toList())

fun Animal.canBreed() = this.energy >= config.energyRequiredToBreed

fun Animal.breed(parent2: Animal): Animal {
    val parent1 = this
    with(parent1.config) {
        if (parent1.canBreed() && parent2.canBreed())
            return Animal(
                config = this,
                position = parent1.position,
                genotype = genotypeOfParents(parent1, parent2),
                energy = 2 * energyGivenToNewborn
            )
        else
            throw IllegalStateException(
                "Attempted to breed animals of IDs ${parent1.id} and ${parent2.id}, " +
                        "but at least one of them was unable to do so"
            )
    }
}

fun Animal.afterDay() = this.copy(
    age = age + 1,
    energy = energy - config.energyConsumedEachDay
)

fun Animal.rotate(): Animal {
    val rotation = genotype[age]
    return this.copy(direction = direction + rotation)
}

fun Animal.afterBreeding(): Animal {
    if (!this.canBreed())
        throw IllegalStateException("Attempted to breed animal of ID $id, but it is unable to do so")
    return this.copy(energy = energy - config.energyGivenToNewborn)
}
