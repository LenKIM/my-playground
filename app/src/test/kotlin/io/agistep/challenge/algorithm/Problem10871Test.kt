package io.agistep.challenge.algorithm

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Problem10871Test {

    @Test
    fun name() {
        val x = 5
        val ints = listOf(1, 10, 4, 9, 2, 3, 8, 5, 7, 6)
        val solved = Problem10871().solved(x, ints)
        assertEquals("1 4 2 3", solved)
    }
}