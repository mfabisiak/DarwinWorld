package io.github.mfabisiak.darwinworld

import io.github.mfabisiak.darwinworld.config.SimulationConfig
import io.github.mfabisiak.darwinworld.model.Position
import io.github.mfabisiak.darwinworld.model.PositionClosedRange
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

data class TestSimulationConfig(
    override val numberOfAnimals: Int = 10,
    override val numberOfPlants: Int = 10,
    override val lowerBound: Position = Position(0, 0),
    override val upperBound: Position = Position(9, 9),
    override val jungle: PositionClosedRange = Position(0, 0)..Position(0, 0),
    override val initialEnergy: Int = 50,
    override val energyConsumedEachDay: Int = 10,
    override val energyRequiredToBreed: Int = 20,
    override val energyGivenToNewborn: Int = 10,
    override val minNumberOfMutations: Int = 0,
    override val maxNumberOfMutations: Int = 0,
    override val genotypeSize: Int = 5,
    override val energyFromSinglePlant: Int = 20,
    override val plantsGrowingEachDay: Int = 0
) : SimulationConfig

class SimulationTest {

    @Test
    fun simulationShouldInitializeWithCorrectNumberOfEntities() {
        // given
        val config = TestSimulationConfig(
            numberOfAnimals = 15,
            plantsGrowingEachDay = 5
        )
        // when
        val simulation = Simulation(config)
        val map = simulation.worldMap.value

        // then
        assertEquals(15, map.animals.size)
        assertEquals(5, map.plants.size)
    }

    @Test
    fun populationShouldDieOutUnderConditions() {
        // given
        val config = TestSimulationConfig(
            numberOfAnimals = 10,
            initialEnergy = 10,
            energyConsumedEachDay = 20,
            plantsGrowingEachDay = 0,
            numberOfPlants = 0
        )

        val simulation = Simulation(config)
        // when
        simulation.simulateDay()
        simulation.simulateDay()

        val map = simulation.worldMap.value
        // then
        assertTrue(map.animals.isEmpty())
        assertEquals(10, map.deadAnimals.size)
    }

    @Test
    fun animalsShouldLoseEnergyOverMultipleDays() {
        // given
        val config = TestSimulationConfig(
            numberOfAnimals = 1,
            initialEnergy = 50,
            energyConsumedEachDay = 10,
            plantsGrowingEachDay = 0
        )

        val simulation = Simulation(config)
        // when
        simulation.simulateDay()
        simulation.simulateDay()
        simulation.simulateDay()

        val animal = simulation.worldMap.value.animals.values.first()
        // then
        assertEquals(20, animal.energy)
        assertEquals(3, animal.age)
    }

    @Test
    fun animalShouldEatPlantAndGainEnergyInSimulationLoop() {
        // given
        val config = TestSimulationConfig(
            upperBound = Position(0, 0),
            numberOfAnimals = 1,
            numberOfPlants = 1,
            initialEnergy = 50,
            energyConsumedEachDay = 20,
            energyFromSinglePlant = 5,
            plantsGrowingEachDay = 0
        )

        val simulation = Simulation(config)
        // when
        simulation.simulateDay()

        val map = simulation.worldMap.value
        val animal = map.animals.values.first()
        // then
        assertEquals(0, map.plants.size)
        assertEquals(50 - 20 + 5, animal.energy)
    }

    @Test
    fun plantsShouldNotExceedMapCapacity() {
        // given
        val config = TestSimulationConfig(
            upperBound = Position(1, 1),
            numberOfAnimals = 0,
            numberOfPlants = 0,
            plantsGrowingEachDay = 10
        )

        val simulation = Simulation(config)
        // when
        simulation.simulateDay()
        simulation.simulateDay()
        simulation.simulateDay()

        val map = simulation.worldMap.value
        // then
        assertEquals(4, map.plants.size)
    }

    @Test
    fun animalsShouldChangePositionAfterDay() {
        // given
        val config = TestSimulationConfig(
            upperBound = Position(10, 10),
            numberOfAnimals = 1,
            plantsGrowingEachDay = 0
        )
        val simulation = Simulation(config)
        val startPosition = simulation.worldMap.value.animals.values.first().position

        // when
        simulation.simulateDay()

        val endPosition = simulation.worldMap.value.animals.values.first().position

        // then
        assertTrue(startPosition != endPosition)
    }

    @Test
    fun populationShouldGrowOnCrowdedMap() {
        // given
        val config = TestSimulationConfig(
            upperBound = Position(1, 1),
            numberOfAnimals = 4,
            initialEnergy = 50,
            energyConsumedEachDay = 1,
            energyRequiredToBreed = 10,
            energyGivenToNewborn = 5,
            plantsGrowingEachDay = 0
        )
        val simulation = Simulation(config)
        val maxDays = 10

        // when
        for (i in 1..maxDays) {
            simulation.simulateDay()
            if (simulation.worldMap.value.animals.size > 4) {
                break
            }
        }

        val map = simulation.worldMap.value

        // then
        assertTrue(map.animals.size > 4)
    }

}


