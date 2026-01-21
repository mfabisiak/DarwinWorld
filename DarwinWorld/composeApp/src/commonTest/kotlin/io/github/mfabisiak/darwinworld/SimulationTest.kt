package io.github.mfabisiak.darwinworld

import io.github.mfabisiak.darwinworld.config.ProductionConfig
import io.github.mfabisiak.darwinworld.model.Position
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SimulationTest {

    @Test
    fun simulationShouldInitializeWithCorrectNumberOfEntities() {
        // given
        val config = ProductionConfig(
            numberOfAnimals = 15,
            numberOfPlants = 5
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
        val config = ProductionConfig(
            numberOfAnimals = 10,
            initialEnergy = 10,
            energyConsumedEachDay = 20,
            plantsGrowingEachDay = 0,
            numberOfPlants = 0
        )

        val simulation = Simulation(config)
        // when
        repeat(2) {
            simulation.simulateDay()
        }

        val map = simulation.worldMap.value
        // then
        assertTrue(map.animals.isEmpty())
        assertEquals(10, map.deadAnimals.size)
    }

    @Test
    fun animalsShouldLoseEnergyOverMultipleDays() {
        // given
        val config = ProductionConfig(
            numberOfPlants = 0,
            numberOfAnimals = 1,
            initialEnergy = 50,
            energyConsumedEachDay = 10,
            plantsGrowingEachDay = 0
        )

        val simulation = Simulation(config)
        // when
        repeat(3) {
            simulation.simulateDay()
        }

        val animal = simulation.worldMap.value.animals.values.first()
        // then
        assertEquals(20, animal.energy)
        assertEquals(3, animal.age)
    }

    @Test
    fun animalShouldEatPlantAndGainEnergyInSimulationLoop() {
        // given
        val config = ProductionConfig(
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
        val config = ProductionConfig(
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
    fun populationShouldGrowOnCrowdedMap() {
        // given
        val config = ProductionConfig(
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
        repeat(maxDays) {
            simulation.simulateDay()
            if (simulation.worldMap.value.animals.size > 4) {
                return@repeat
            }
        }

        val map = simulation.worldMap.value

        // then
        assertTrue(map.animals.size > 4)
    }

}


