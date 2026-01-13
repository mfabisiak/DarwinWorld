package io.github.mfabisiak.darwinworld.model.animal

import io.github.mfabisiak.darwinworld.config.AnimalConfig
import io.github.mfabisiak.darwinworld.model.Direction
import io.github.mfabisiak.darwinworld.model.Position
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

data class TestConfig(
    override val energyConsumedEachDay: Int = 10,
    override val energyRequiredToBreed: Int = 40,
    override val energyGivenToNewborn: Int = 20,
    override val minNumberOfMutations: Int = 0,
    override val maxNumberOfMutations: Int = 5,
    override val genotypeSize: Int = 5
) : AnimalConfig

class AnimalBreedingTest {
    @Test
    fun breedShouldThrowExceptionWithNotEnoughEnergy() {
        // given
        val config = TestConfig()

        val animal1 = Animal(
            config,
            Position(1, 1),
            30,
            Genotype(listOf(1, 0, 0, 5, 4)),
            Direction.NORTH
        )

        val animal2 = Animal(
            config,
            Position(1, 1),
            50,
            Genotype(listOf(1, 0, 0, 5, 4)),
            Direction.NORTH
        )
        // when + then
        with(config) {
            assertFailsWith<IllegalStateException> { animal1.breed(animal2) }
        }

    }

    @Test
    fun childShouldHaveInitialEnergyEqualToParentsUsedOnBreeding(){
        // given
        val config = TestConfig()

        val animal1 = Animal(
            config,
            Position(1, 1),
            100,
            Genotype(listOf(1, 0, 0, 5, 4)),
            Direction.NORTH
        )

        val animal2 = Animal(
            config,
            Position(1, 1),
            100,
            Genotype(listOf(1, 0, 0, 5, 4)),
            Direction.NORTH
        )
        // when + then
        assertEquals(2 * config.energyGivenToNewborn, animal1.breed(animal2).energy)
    }

    @Test
    fun childShouldHaveEqualLengthOfGenotypeAsParents(){
        // given
        val config = TestConfig()

        val animal1 = Animal(
            config,
            Position(1, 1),
            100,
            Genotype(listOf(1, 0, 0, 5, 4)),
            Direction.NORTH
        )

        val animal2 = Animal(
            config,
            Position(1, 1),
            100,
            Genotype(listOf(1, 0, 0, 5, 4)),
            Direction.NORTH
        )
        // when + then
        assertEquals(config.genotypeSize, animal2.breed(animal1).genotype.genes.size)
    }

    @Test
    fun childShouldHaveGenesOnlyFromParentsWhenMutationsAreDisabled(){
        // given
        val config = TestConfig(maxNumberOfMutations = 0, genotypeSize = 25)

        val allowedGenes = listOf(1, 2, 3)

        val animal1 = Animal(
            config,
            Position(1, 1),
            100,
            Genotype(List(25) {Random.nextInt(1,3)}),
            Direction.NORTH
        )

        val animal2 = Animal(
            config,
            Position(1, 1),
            100,
            Genotype(List(25) {Random.nextInt(1,3)}),
            Direction.NORTH
        )
        // when
        val child = animal1.breed(animal2)
            .genotype
            .genes
            .infiniteIterator()
            .asSequence()
            .take(25)
            .toList()
        // then
        assertEquals(true, child.all { it in allowedGenes })
    }

    @Test
    fun strongerParentShouldContributeMoreGenes(){
        // given
        val config = TestConfig(genotypeSize = 10, minNumberOfMutations = 0, maxNumberOfMutations = 0 , energyRequiredToBreed = 10)

        val strongParent = Animal(config, Position(0,0), 90, Genotype(List(10){ 1 }), Direction.NORTH)

        val weakParent = Animal(config, Position(0,0), 10, Genotype(List(10){ 2 }), Direction.NORTH)
        // when
        val genes = strongParent.breed(weakParent)
            .genotype
            .genes
            .iterator()
            .asSequence()
            .take(10)
            .toList()

        val countFromStrongParent = genes.count { it == 1 }

        // then
        assertEquals(9, countFromStrongParent)
    }
}