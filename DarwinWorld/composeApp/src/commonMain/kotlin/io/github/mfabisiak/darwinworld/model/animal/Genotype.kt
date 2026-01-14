package io.github.mfabisiak.darwinworld.model.animal

import io.github.mfabisiak.darwinworld.utils.CircularList
import kotlin.random.Random

val GENE_RANGE = 0..7

class Genotype(genesList: List<Int>) {

    val genes = CircularList(genesList)

    private val firstDayGene = Random.nextInt(genes.size)

    operator fun get(day: Int) = genes[firstDayGene + day]
}




