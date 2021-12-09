package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {

    private val input = readInputFile("09")

    @Test
    fun validateFirstSampleInputs() {
        val sampleGridText = listOf(
            "2199943210",
            "3987894921",
            "9856789892",
            "8767896789",
            "9899965678",
        )
        assertEquals(15, Day09.solveFirst(sampleGridText))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(607, Day09.solveFirst(input))
    }
}
