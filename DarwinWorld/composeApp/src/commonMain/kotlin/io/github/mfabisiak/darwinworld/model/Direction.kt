package io.github.mfabisiak.darwinworld.model

enum class Direction {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;


    operator fun plus(other: Int) = Direction.entries[(this.ordinal + other).mod(numberOfDirections)]

    operator fun minus(other:Int) = Direction.entries[(this.ordinal + other).mod(numberOfDirections)]
}

private val numberOfDirections = Direction.entries.size
