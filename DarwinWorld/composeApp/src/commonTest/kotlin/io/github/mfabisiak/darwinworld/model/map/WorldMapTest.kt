package io.github.mfabisiak.darwinworld.model.map

import io.github.mfabisiak.darwinworld.config.AnimalConfig
import io.github.mfabisiak.darwinworld.config.MapConfig
import io.github.mfabisiak.darwinworld.model.Direction
import io.github.mfabisiak.darwinworld.model.Position
import io.github.mfabisiak.darwinworld.model.PositionClosedRange
import io.github.mfabisiak.darwinworld.model.animal.Animal
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.persistentSetOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

data class TestMapConfig(
    override val lowerBound: Position = Position(0, 0),
    override val upperBound: Position = Position(4, 4),
    override val jungle: PositionClosedRange = Position(0, 2)..Position(4, 3),
    override val initialEnergy: Int = 100,
    override val energyConsumedEachDay: Int = 0,
    override val energyRequiredToBreed: Int = 50,
    override val energyGivenToNewborn: Int = 20,
    override val minNumberOfMutations: Int = 0,
    override val maxNumberOfMutations: Int = 0,
    override val genotypeSize: Int = 5,
    override val energyFromSinglePlant: Int = 20,
    override val plantsGrowingEachDay: Int = 1
) : MapConfig

class WorldMapTest {

    @Test
    fun strongestAnimalShouldEatFirstWhenConflictOccurs(){
        // given
        val weakConfig = TestMapConfig(initialEnergy = 20)
        val strongConfig = TestMapConfig(initialEnergy = 70)

        val pos = Position(2,3)
        val weakAnimal = Animal(weakConfig, pos)
        val strongAnimal = Animal(strongConfig, pos)

        val map = WorldMap(
            weakConfig,
            animals = persistentMapOf(
                weakAnimal.id to weakAnimal,
                strongAnimal.id to strongAnimal
            ),
            plants = persistentSetOf(pos)
        )
        // when
        val newMap = map.eatPlants()

        val weakAfter = newMap.animals[weakAnimal.id]!!
        val strongAfter = newMap.animals[strongAnimal.id]!!
        // then
        assertEquals(20,weakAfter.energy)
        assertEquals(90,strongAfter.energy)
        assertTrue(newMap.plants.isEmpty())

    }

    @Test
    fun olderAnimalShouldEatFirstWhenEnergyIsEqual() {
        // given
        val config = TestMapConfig(energyFromSinglePlant = 20)
        val pos = Position(1, 1)

        val youngerAnimal = Animal(config, position = pos, energy = 50, age = 2)
        val olderAnimal = Animal(config, position = pos, energy = 50, age = 10)

        val map = WorldMap(
            config,
            animals = persistentMapOf(
                youngerAnimal.id to youngerAnimal,
                olderAnimal.id to olderAnimal
            ),
            plants = persistentSetOf(pos)
        )
        // when
        val newMap = map.eatPlants()

        val youngerAfter = newMap.animals[youngerAnimal.id]!!
        val olderAfter = newMap.animals[olderAnimal.id]!!
        // then
        assertEquals(50, youngerAfter.energy)
        assertEquals(70, olderAfter.energy)
        assertTrue(newMap.plants.isEmpty())
    }

    @Test
    fun animalWithMoreChildrenShouldEatFirstWhenEnergyAndAgeAreEqual() {
        // given
        val config = TestMapConfig()
        val pos = Position(1, 1)

        val childlessAnimal = Animal(config, position = pos, energy = 50, age = 10)

        val parentAnimal = Animal(
            config,
            position = pos,
            energy = 50,
            age = 10,
            childrenIds = persistentSetOf("child1", "child2")
        )

        val map = WorldMap(
            config,
            animals = persistentMapOf(
                childlessAnimal.id to childlessAnimal,
                parentAnimal.id to parentAnimal
            ),
            plants = persistentSetOf(pos)
        )
        // when
        val newMap = map.eatPlants()

        val childlessAfter = newMap.animals[childlessAnimal.id]!!
        val parentAfter = newMap.animals[parentAnimal.id]!!
        // then
        assertEquals(50, childlessAfter.energy)
        assertEquals(70, parentAfter.energy)
    }

    @Test
    fun animalShouldWrapAroundMapHorizontally() {
        // given
        val config = TestMapConfig(upperBound = Position(2, 2))
        val startPos = Position(2, 1)

        val animal = Animal(
            config = config,
            position = startPos,
            direction = Direction.EAST
        )
        val map = WorldMap(config, animals = persistentMapOf(animal.id to animal))
        // when
        val newMap = map.moveAnimals()

        val movedAnimal = newMap.animals.values.first()

        // then
        assertEquals(0, movedAnimal.position.x)
        assertEquals(1, movedAnimal.position.y)
    }

    @Test
    fun animalShouldBounceOffPoleAndReverseDirection() {
        // given
        val config = TestMapConfig(upperBound = Position(2, 2))
        val startPos = Position(1, 2)

        val animal = Animal(
            config = config,
            position = startPos,
            direction = Direction.NORTH
        )
        val map = WorldMap(config, animals = persistentMapOf(animal.id to animal))
        // when
        val newMap = map.moveAnimals()

        val movedAnimal = newMap.animals.values.first()
        // then
        assertEquals(1, movedAnimal.position.x)
        assertEquals(2, movedAnimal.position.y)
        assertEquals(Direction.SOUTH, movedAnimal.direction)
    }

    @Test
    fun deadAnimalsShouldBeRemovedAndAddedToHistory() {
        // given
        val config = TestMapConfig()
        val deadAnimal = Animal(config, Position(1,1), energy = 0)
        val aliveAnimal = Animal(config, Position(1,2), energy = 10)

        val map = WorldMap(
            config,
            animals = persistentMapOf(
                deadAnimal.id to deadAnimal,
                aliveAnimal.id to aliveAnimal
            )
        )
        // when
        val newMap = map.removeDead()

        // then
        assertEquals(1, newMap.animals.size)
        assertTrue(newMap.animals.containsKey(aliveAnimal.id))

        assertEquals(1, newMap.deadAnimals.size)
        assertTrue(newMap.deadAnimals.containsKey(deadAnimal.id))
    }

    @Test
    fun plantsShouldSpawnOnEmptyMap() {
        // given
        val config = TestMapConfig(
            plantsGrowingEachDay = 5,
            upperBound = Position(9, 9)
        )

        val map = WorldMap(config)
        // when
        val newMap = map.spawnPlants()
        // then
        assertEquals(5, newMap.plants.size)
    }

    @Test
    fun animalsShouldAgeAndLoseEnergyAtEndOfDay() {
        // given
        val config = TestMapConfig(energyConsumedEachDay = 5)
        val animal = Animal(config, Position(1,1), age = 0, energy = 20)

        val map = WorldMap(
            config,
            animals = persistentMapOf(animal.id to animal)
        )
        // when
        val newMap = map.endDay()
        val animalAfter = newMap.animals[animal.id]!!
        // then
        assertEquals(1, animalAfter.age)
        assertEquals(15, animalAfter.energy)
    }
}
