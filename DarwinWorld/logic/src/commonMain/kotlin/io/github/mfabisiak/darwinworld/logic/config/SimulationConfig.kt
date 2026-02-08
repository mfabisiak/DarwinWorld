package io.github.mfabisiak.darwinworld.logic.config

import kotlin.uuid.Uuid

interface SimulationConfig : MapConfig {
    val numberOfPlants: Int
    val numberOfAnimals: Int
    val id: String
}
