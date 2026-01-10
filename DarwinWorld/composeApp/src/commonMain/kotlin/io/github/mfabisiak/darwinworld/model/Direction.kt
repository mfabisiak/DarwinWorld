package io.github.mfabisiak.darwinworld.model


enum class Direction(val index: Int) {
    NORTH(0),
    NORTH_EAST(1),
    EAST(2),
    SOUTH_EAST(3),
    SOUTH(4),
    SOUTH_WEST(5),
    WEST(6),
    NORTH_WEST(7);

    operator fun plus(other:Int) : Direction = Direction.entries[(this.index+other)%8]

    operator fun minus(other:Int) : Direction{
        val directionAfterRotate = if (this.index-other>0){
            (this.index-other)%8
        } else {
            (this.index-other+8)%8
        }
        return Direction.entries[directionAfterRotate]
    }
}