package io.github.mfabisiak.darwinworld.config

import io.github.mfabisiak.darwinworld.model.Position
import io.github.mfabisiak.darwinworld.model.PositionClosedRange

interface MapConfig : AnimalConfig {
    val lowerBound: Position
    val upperBound: Position
    val energyFromSinglePlant: Int
    val plantsGrowingEachDay: Int
    val jungle: PositionClosedRange

    val boundary: PositionClosedRange
        get() = lowerBound..upperBound
}