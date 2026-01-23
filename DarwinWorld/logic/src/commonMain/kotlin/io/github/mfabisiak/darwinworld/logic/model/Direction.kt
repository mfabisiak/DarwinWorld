package io.github.mfabisiak.darwinworld.logic.model

import io.github.mfabisiak.darwinworld.logic.utils.CircularList
import kotlin.random.Random

enum class Direction {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    companion object {
        fun random(random: Random = Random) = Direction.entries.random(random)
    }


    operator fun plus(other: Int) = directionsList[this.ordinal + other]

    operator fun minus(other:Int) = directionsList[this.ordinal - other]

    operator fun unaryMinus() = directionsList[this.ordinal + 4]
}

private val directionsList =
    CircularList(Direction.entries)
