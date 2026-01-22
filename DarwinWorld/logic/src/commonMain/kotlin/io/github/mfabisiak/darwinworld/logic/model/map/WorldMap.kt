package io.github.mfabisiak.darwinworld.logic.model.map

import io.github.mfabisiak.darwinworld.logic.config.MapConfig
import io.github.mfabisiak.darwinworld.logic.model.Position
import io.github.mfabisiak.darwinworld.logic.model.animal.Animal
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.persistentSetOf


typealias Animals = PersistentMap<String, Animal>
typealias Plants = PersistentSet<Position>

data class WorldMap(
    val config: MapConfig,
    val animals: Animals = persistentMapOf(),
    val deadAnimals: Animals = persistentMapOf(),
    val plants: Plants = persistentSetOf(),
)
