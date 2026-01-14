package io.github.mfabisiak.darwinworld.model.map

import io.github.mfabisiak.darwinworld.config.MapConfig
import io.github.mfabisiak.darwinworld.model.Position
import io.github.mfabisiak.darwinworld.model.animal.Animal
import io.github.mfabisiak.darwinworld.model.movement
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.persistentSetOf


typealias Animals = PersistentMap<Position, PersistentSet<Animal>>
typealias Plants = PersistentSet<Position>

data class WorldMap(
    val config: MapConfig,
    val animals: Animals = persistentMapOf(),
    val plants: Plants = persistentSetOf(),
    val animalsDict: PersistentMap<String, Animal> = persistentMapOf()
) {

    fun addPlant(position: Position) = this.copy(plants = plants.add(position))

    fun eatPlant(position: Position): WorldMap {
        if (position !in plants || position !in animals.keys) return this

        val animal = animals[position]!!
            .shuffled()
            .maxWithOrNull(compareBy<Animal> { it.energy }
                .thenBy { it.age }
                .thenBy { it.childrenIds.size })

        if (animal == null) {
            return this.copy(animals = animals.remove(position))
        }

        val newEnergy = animal.energy + config.energyFromSinglePlant
        val newAnimal = animal.copy(energy = newEnergy)

        val newPlants = plants.remove(position)
        val newAnimals = animals.update(animal, newAnimal)
        val newAnimalsDict = animalsDict.put(newAnimal.id, newAnimal)


        return this.copy(plants = newPlants, animals = newAnimals, animalsDict = newAnimalsDict)
    }

    fun moveAnimal(animal: Animal): WorldMap {
        val newAnimal: Animal = animal.copy(position = animal.position + animal.direction.movement())

        val newAnimals = animals.update(animal, newAnimal)
        val newAnimalsDict = animalsDict.put(animal.id, newAnimal)

        return this.copy(animals = newAnimals, animalsDict = newAnimalsDict)
    }

    fun addAnimal(animal: Animal): WorldMap {
        val animalsAtPosition = (animals[animal.position] ?: persistentSetOf())
            .add(animal)

        val newAnimalsDict = animalsDict.put(animal.id, animal)
        val newAnimals = animals.put(animal.position, animalsAtPosition)

        return this.copy(animals = newAnimals, animalsDict = newAnimalsDict)
    }


    fun removeDead(): WorldMap {
        val newAnimals = animals.values
            .flatten()
            .filter { !it.isAlive }
            .fold(animals) { animals, animal -> animals.removeAnimal(animal) }

        return this.copy(animals = newAnimals)
    }

}

private fun Animals.removeAnimal(animal: Animal): Animals {
    val animalsAtPosition = (this[animal.position]
        ?: throw IllegalStateException("Attempted to remove non existing animal: $animal."))
        .remove(animal)

    val newAnimals = if (animalsAtPosition.isEmpty()) {
        this.remove(animal.position)
    } else {
        this.put(animal.position, animalsAtPosition)
    }

    return newAnimals
}

private fun Animals.update(oldAnimal: Animal, newAnimal: Animal): Animals {
    val newAnimalsAtOldPosition = this[oldAnimal.position]?.remove(oldAnimal)
        ?: throw IllegalStateException("Attempted to update non existing animal: $this.")

    val newAnimalsAtNewPosition = this[newAnimal.position]?.add(newAnimal) ?: persistentSetOf(newAnimal)


    val newAnimals = if (newAnimalsAtOldPosition.isNotEmpty()) {
        this
            .put(oldAnimal.position, newAnimalsAtOldPosition)
            .put(newAnimal.position, newAnimalsAtNewPosition)
    } else {
        this
            .remove(oldAnimal.position)
            .put(newAnimal.position, newAnimalsAtNewPosition)
    }

    return newAnimals
}