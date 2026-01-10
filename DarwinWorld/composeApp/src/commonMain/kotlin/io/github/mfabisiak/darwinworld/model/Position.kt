package io.github.mfabisiak.darwinworld.model
import io.github.mfabisiak.darwinworld.model.Direction.*

data class Position(val x: Int, val y: Int) {

    operator fun plus(other: Position): Position = Position(this.x + other.x, this.y + other.y)

    operator fun minus(other: Position): Position = Position(this.x - other.x, this.y - other.y)

    infix fun precedes(other: Position): Boolean = this.x <= other.x && this.y <= other.y

    infix fun follows(other: Position): Boolean = this.x >= other.x && this.y >= other.y

}

fun Direction.movement(): Position = when(this) {
        NORTH -> Position(0, 1)
        NORTH_EAST -> Position(1, 1)
        EAST -> Position(1, 0)
        SOUTH_EAST -> Position(1, -1)
        SOUTH -> Position(0, -1)
        SOUTH_WEST -> Position(-1, -1)
        WEST -> Position(-1,0)
        NORTH_WEST -> Position(-1, 1)
    }


