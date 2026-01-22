package io.github.mfabisiak.darwinworld.logic.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class CircularListTest {
    @Test
    fun shouldReturnElementsInCorrectOrder(){
        // given
        val list = CircularList(listOf(1, 2, 3))

        val result = list.infiniteIterator()
            .asSequence()
            .take(7)
            .toList()
       // when + then
        assertEquals(listOf(1, 2, 3, 1, 2, 3, 1),result)
    }
    @Test
    fun shouldThrowAnExceptionWithEmptyList(){
        // given + when + then
        assertFailsWith<IllegalArgumentException> {
            CircularList(listOf<Int>())
        }
    }
    @Test
    fun shouldReturnElementsInCorrectOrderWithRandomStartIndex(){
        // given
        val list = CircularList(listOf(0, 1, 2, 3))

        val pattern = listOf(0, 1, 2, 3, 0, 1, 2, 3).joinToString()

        val result = list.randomStartingInfiniteIterator()
            .asSequence()
            .take(5)
            .toList()
            .joinToString()
        // when + then
        assertTrue(
            pattern.contains(result)
        )
    }
    @Test
    fun iteratorShouldAlwaysHaveNextElement(){
        // given
        val list = CircularList(listOf(0, 1, 2, 3, 4, 5))

        val iterator = list.randomStartingInfiniteIterator()
        // when
        repeat(1000) {iterator.next()}
        // then
        assertTrue(iterator.hasNext())
    }
    @Test
    fun shouldWorkCorrectlyWithSingleElementList(){
        // given
        val list = CircularList(listOf(1))

        val result = list.infiniteIterator()
            .asSequence()
            .take(10)
            .toList()
        // when + then
        assertEquals(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1), result)
    }
}