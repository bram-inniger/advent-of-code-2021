package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {

    private val input = readInputFile("09")
    private val sampleGridText = listOf(
        "2199943210",
        "3987894921",
        "9856789892",
        "8767896789",
        "9899965678",
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(15, Day09.solveFirst(sampleGridText))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(607, Day09.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(1_134, Day09.solveSecond(sampleGridText))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(900_864, Day09.solveSecond(input))
    }
}
