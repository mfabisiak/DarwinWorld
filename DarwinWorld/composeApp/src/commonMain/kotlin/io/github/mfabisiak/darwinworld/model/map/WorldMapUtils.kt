package io.github.mfabisiak.darwinworld.model.map

import io.github.mfabisiak.darwinworld.model.Position
import io.github.mfabisiak.darwinworld.model.animal.*

fun Animals.update(newAnimal: Animal): Animals {
    return this.put(newAnimal.id, newAnimal)
}

private fun WorldMap.eatPlant(animalsAtPosition: Collection<Animal>): WorldMap {
    if (animalsAtPosition.isEmpty()) return this

    val position = animalsAtPosition.first().position

    if (position !in plants) return this

    val animal = animalsAtPosition
        .shuffled()
        .maxWith(compareBy<Animal> { it.energy }
            .thenBy { it.age }
            .thenBy { it.childrenIds.size })


    val newAnimals = animals.update(animal.copy(energy = animal.energy + config.energyFromSinglePlant))

    val newPlants = plants.remove(position)

    return this.copy(animals = newAnimals, plants = newPlants)

}

fun WorldMap.eatPlants(): WorldMap = animals.values
    .groupBy { it.position }
    .filter { it.key in plants }
    .values
    .fold(this) { currentMap, animalsAtPosition ->
        currentMap.eatPlant(animalsAtPosition)
    }

fun WorldMap.rotateAnimals(): WorldMap {
    val newAnimals = animals.values
        .fold(animals) { currentAnimals, animal ->
            currentAnimals.update(animal.rotate())
        }

    return this.copy(animals = newAnimals)
}

fun WorldMap.moveAnimals(): WorldMap {
    val newAnimals = animals.values
        .fold(animals) { currentAnimals, animal ->
            currentAnimals.update(animal.move())
        }

    return this.copy(animals = newAnimals)
}

fun WorldMap.removeDead(): WorldMap {
    val (newAnimals, newDeadAnimals) = animals.values
        .fold(animals to deadAnimals) { (currentAnimals, currentDeadAnimals), animal ->
            currentAnimals.remove(animal.id) to currentDeadAnimals.update(animal)
        }

    return this.copy(animals = newAnimals, deadAnimals = newDeadAnimals)
}

fun WorldMap.placeAnimals(positions: Collection<Position>): WorldMap {
    val newAnimals = positions
        .fold(animals) { currentAnimals, position ->
            currentAnimals.update(Animal(config, position))
        }

    return this.copy(animals = newAnimals)
}

private fun Animals.breed() = this.values
    .filter { it.canBreed }
    .groupBy { it.position }
    .values
    .filter { it.size >= 2 }
    .map { animalsAtPosition ->
        animalsAtPosition.shuffled()
            .sortedWith(compareBy<Animal> { it.energy }
                .thenBy { it.age }
                .thenBy { it.childrenIds.size })
            .takeLast(2)
    }
    .fold(this) { currentAnimals, (parent1, parent2) ->
        currentAnimals
            .update(parent1.breed(parent2))
            .update(parent1.afterBreeding())
            .update(parent2.afterBreeding())
    }

fun WorldMap.breedAnimals() = this.copy(animals = animals.breed())

fun WorldMap.addPlant(position: Position) = this.copy(plants = plants.add(position))

private fun Animals.endDay() = this.values
    .fold(this) { currentAnimals, animal ->
        currentAnimals.update(animal.afterDay())
    }

fun WorldMap.endDay() = this.copy(animals = animals.endDay())