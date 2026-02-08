package io.github.mfabisiak.darwinworld.logic.model.animal

import io.github.mfabisiak.darwinworld.logic.utils.CircularList
import kotlin.random.Random

val GENE_RANGE = 0..7

class Genotype(genesList: List<Int>, random: Random = Random) {

    val genes = CircularList(genesList)

    private val firstDayGene = random.nextInt(genes.size)

    operator fun get(day: Int) = genes[firstDayGene + day]
}




