package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {

    private val input = readInputFile("01").map(String::toInt)
    private val sampleMeasurements = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(7, Day01.solveFirst(sampleMeasurements))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(1_466, Day01.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(5, Day01.solveSecond(sampleMeasurements))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(1_491, Day01.solveSecond(input))
    }
}
