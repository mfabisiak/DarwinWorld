package io.github.mfabisiak.darwinworld.ui.simulation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import io.github.mfabisiak.darwinworld.logic.model.animal.Animal
import io.github.mfabisiak.darwinworld.logic.model.map.WorldMap
import io.github.mfabisiak.darwinworld.logic.model.map.utils.numberOfDescendants

@Composable
fun AnimalStatistics(worldMap: WorldMap, trackedAnimal: Animal?) {
    if (trackedAnimal != null) {
        Text("Informacje o obserwowanym zwierzaku")
        Text("Status ${if (trackedAnimal.energy > 0) "Żywy" else "Martwy"}")
        val genotype = trackedAnimal.genotype.genes.actualList.joinToString(" ")
        Text("Genom [$genotype]")
        Text("Energia: ${trackedAnimal.energy}")
        Text("Zjedzone rośliny: ${trackedAnimal.eatenPlants}")
        Text("Dzieci: ${trackedAnimal.childrenIds.size}")
        Text("Potomkowie: ${worldMap.numberOfDescendants(trackedAnimal.id)}")
        if (trackedAnimal.energy > 0) {
            Text("Wiek: ${trackedAnimal.age}")
        } else {
            Text("Zmarł w dniu: ${trackedAnimal.birthDay + trackedAnimal.age}")
        }
    }
}