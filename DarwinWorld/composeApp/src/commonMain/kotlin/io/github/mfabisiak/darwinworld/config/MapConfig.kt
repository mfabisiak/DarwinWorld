package io.github.mfabisiak.darwinworld.config

import io.github.mfabisiak.darwinworld.model.Position

interface MapConfig {
    val lowerBound: Position
    val upperBound: Position
}