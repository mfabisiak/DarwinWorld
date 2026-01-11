package io.github.mfabisiak.darwinworld.model.animal

import io.github.mfabisiak.darwinworld.utils.CircularList

val GENE_RANGE = 0..7

class Genotype(genesList: List<Int>) {

    val genes = CircularList(genesList)

    private val genesIterator = genes.randomStartingInfiniteIterator()

    fun nextGene() = genesIterator.next()
}




