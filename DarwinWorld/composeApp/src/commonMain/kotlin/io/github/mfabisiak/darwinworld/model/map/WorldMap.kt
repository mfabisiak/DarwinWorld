package io.github.mfabisiak.darwinworld.model.map

import io.github.mfabisiak.darwinworld.config.MapConfig
import io.github.mfabisiak.darwinworld.model.Position
import io.github.mfabisiak.darwinworld.model.animal.Animal
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WorldMap(val config: MapConfig) {

    private val _mapState = MutableStateFlow(MapState())

    val mapState = _mapState.asStateFlow()

    fun addPlant(position: Position) {
        _mapState.update { currentState ->
            val newPlants = currentState.plants.add(position)
            currentState.copy(plants = newPlants)
        }
    }

    private fun removePlant(position: Position, oldPlants: PersistentSet<Position>) =
        oldPlants.remove(position)

    private fun Animal.update(
        newAnimal: Animal,
        oldAnimals: PersistentMap<Position, PersistentSet<Animal>>
    ): PersistentMap<Position, PersistentSet<Animal>> {
        val newAnimalsAtOldPosition = oldAnimals[this.position]?.remove(this)
        val newAnimalsAtNewPosition = oldAnimals[newAnimal.position]?.add(newAnimal) ?: persistentSetOf(newAnimal)

        if (newAnimalsAtOldPosition == null)
            return oldAnimals

        val newAnimals = if (newAnimalsAtOldPosition.isNotEmpty()) {
            oldAnimals
                .put(this.position, newAnimalsAtOldPosition)
                .put(newAnimal.position, newAnimalsAtNewPosition)
        } else {
            oldAnimals
                .remove(this.position)
                .put(newAnimal.position, newAnimalsAtNewPosition)
        }

        return newAnimals
    }


    fun Animal.eatPlant() {
        _mapState.update { currentState: MapState ->
            if (this.position !in currentState.plants) return@update currentState

            val oldPlants = currentState.plants
            val oldAnimals = currentState.animals
            val newEnergy = this.energy + this@WorldMap.config.energyFromSinglePlant

            val newPlants = removePlant(this.position, oldPlants)
            val newAnimals = this.update(this.copy(energy = newEnergy), oldAnimals)

            currentState.copy(plants = newPlants, animals = newAnimals)
        }
    }

    fun Animal.move(vector: Position) {
        _mapState.update { currentState: MapState ->
            val oldAnimals = currentState.animals
            val newAnimal = this.copy(position = this.position + vector)
            val newAnimals = this.update(newAnimal, oldAnimals)

            currentState.copy(animals = newAnimals)
        }
    }

}

data class MapState(
    val plants: PersistentSet<Position> = persistentSetOf(),
    val animals: PersistentMap<Position, PersistentSet<Animal>> = persistentMapOf()
)