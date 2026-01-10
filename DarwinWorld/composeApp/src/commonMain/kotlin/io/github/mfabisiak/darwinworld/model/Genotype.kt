package io.github.mfabisiak.darwinworld.model

import kotlin.random.Random

class Genotype(private val genes: List<Int>) {
    private var currentIndexOfGene=Random.nextInt(genes.size)

    fun nextRotation() : Int {
        currentIndexOfGene+=1
        return genes[currentIndexOfGene % genes.size]
    }
    companion object{
        fun random(length: Int): Genotype = Genotype(List(length) { (0..7).random()})

        fun randomOfParents(parent1: Animal, parent2: Animal) : Genotype{
            val totalEnergy = parent1.energy+parent2.energy
            val divisionOfGenes = parent1.energy * parent1.genotype.genes.size / totalEnergy
            val shareParent1 = divisionOfGenes
            val shareParent2 = parent1.genotype.genes.size - divisionOfGenes
            val sideLeft = Random.nextBoolean()
            if (sideLeft){
                return Genotype(parent1.genotype.genes.take(shareParent1) + parent2.genotype.genes.takeLast(shareParent2))
            }else{
                return Genotype( parent2.genotype.genes.take(shareParent2) + parent1.genotype.genes.takeLast(shareParent1))
            }

        }
    }

}