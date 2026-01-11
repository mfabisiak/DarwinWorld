package io.github.mfabisiak.darwinworld.model

import io.github.mfabisiak.darwinworld.utils.CircularList

enum class Direction {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;


    operator fun plus(other: Int) = directionsList[this.ordinal + other]

    operator fun minus(other:Int) = directionsList[this.ordinal - other]
}

private val directionsList = CircularList(Direction.entries)
