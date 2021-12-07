package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day07Test {

    private val input = readInputFile("07")[0].split(",").map { it.toInt() }

    @Test
    fun validateFirstSampleInputs() {
        val sampleCrabs = listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14)
        assertEquals(37, Day07.solveFirst(sampleCrabs))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(355_764, Day07.solveFirst(input))
    }
}
