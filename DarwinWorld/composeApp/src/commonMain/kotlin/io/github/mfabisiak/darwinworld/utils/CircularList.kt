package io.github.mfabisiak.darwinworld.utils

import kotlin.random.Random

class CircularList<E>(val actualList: List<E>) : List<E> by actualList {

    override operator fun get(index: Int) = actualList[index.mod(size)]

    fun infiniteIteratorFrom(startingIndex: Int) = iterator {
        var currentIndex = startingIndex

        while (true) {
            yield(this@CircularList[currentIndex])
            currentIndex = (currentIndex + 1) % size
        }
    }

    public fun randomStartingInfiniteIterator() = infiniteIteratorFrom(Random.nextInt(size))

    fun infiniteIterator() = infiniteIteratorFrom(0)

}