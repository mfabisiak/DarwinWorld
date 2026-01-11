package io.github.mfabisiak.darwinworld.model.animal

import io.github.mfabisiak.darwinworld.model.Direction
import io.github.mfabisiak.darwinworld.utils.AnimalConfig
import kotlin.random.Random


fun AnimalConfig.genotypeOfParents(parent1: Animal, parent2: Animal): Genotype {

    val (leftParent, rightParent) = setOf(parent1, parent2).shuffled()

    val divisionIndex = leftParent.energy / (leftParent.energy + rightParent.energy)

    val mutatingGenes = GENE_RANGE.shuffled()
        .take(Random.nextInt(minNumberOfMutations, maxNumberOfMutations - 1))

    val genesList = listOf(
        leftParent.genotype.genes.take(divisionIndex),
        rightParent.genotype.genes.takeLast(genotypeSize - divisionIndex)
    ).flatten().mapIndexed { index, gene ->
        if (index in mutatingGenes) GENE_RANGE.random() else gene
    }

    return Genotype(genesList)

}

fun AnimalConfig.animalOfParents(parent1: Animal, parent2: Animal) = Animal(
    config = this,
    position = parent1.position,
    genotype = genotypeOfParents(parent1, parent2),
    initialEnergy = energyRequiredToBreed,
    direction = Direction.random()
)
