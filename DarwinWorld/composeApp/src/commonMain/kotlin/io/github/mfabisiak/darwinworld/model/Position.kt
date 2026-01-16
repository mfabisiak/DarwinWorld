package io.github.mfabisiak.darwinworld.model
import io.github.mfabisiak.darwinworld.model.Direction.*

data class Position(val x: Int, val y: Int) {

    operator fun plus(other: Position): Position = Position(this.x + other.x, this.y + other.y)

    operator fun minus(other: Position): Position = Position(this.x - other.x, this.y - other.y)

    infix fun precedes(other: Position): Boolean = this.x <= other.x && this.y <= other.y

    infix fun follows(other: Position): Boolean = this.x >= other.x && this.y >= other.y

    operator fun rangeTo(other: Position) = PositionClosedRange(this, other)

    infix fun over(range: PositionClosedRange) = this.y > range.end.y

    infix fun under(range: PositionClosedRange) = this.y < range.start.y
    
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


class PositionClosedRange(val start: Position, val end: Position) : Iterable<Position> {
    val width
        get() = end.x - start.x + 1

    val height
        get() = end.y - start.y + 1

    override fun iterator(): Iterator<Position> = Vector2dIterator(start, end)

    operator fun contains(position: Position) = position follows start && position precedes end

    fun random() = this.toSet().random()

    fun random(n: Int) = this.toSet().shuffled().take(n)

    private class Vector2dIterator(val start: Position, val end: Position) : Iterator<Position> {
        private var current = start

        override fun hasNext(): Boolean = current precedes end

        override fun next(): Position {
            if (!hasNext()) throw NoSuchElementException()

            val previous = current

            current = if (current.x < end.x) {
                Position(current.x + 1, current.y)
            } else {
                Position(start.x, current.y + 1)
            }

            return previous
        }
    }

}