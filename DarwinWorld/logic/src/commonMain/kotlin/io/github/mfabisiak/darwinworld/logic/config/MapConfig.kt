package io.github.mfabisiak.darwinworld.logic.config

import io.github.mfabisiak.darwinworld.logic.model.Position
import io.github.mfabisiak.darwinworld.logic.model.PositionClosedRange

interface MapConfig : AnimalConfig {
    val lowerBound: Position
    val upperBound: Position
    val energyFromSinglePlant: Int
    val plantsGrowingEachDay: Int
    val jungle: PositionClosedRange
    val energyRequiredToMoveFast: Int
    val energyPerExtraStep: Int
    val maxRange: Int
    val boundary: PositionClosedRange
        get() = lowerBound..upperBound
}