package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Test {

    private val input = readInputFile("17")[0]
    private val sampleTarget = "target area: x=20..30, y=-10..-5"

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(45, Day17.solveFirst(sampleTarget))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(23_005, Day17.solveFirst(input))
    }
}
