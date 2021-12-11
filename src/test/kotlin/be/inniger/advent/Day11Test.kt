package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {

    private val input = readInputFile("11")

    @Test
    fun validateFirstSampleInputs() {
        val sampleChangeMe = listOf(
            "5483143223",
            "2745854711",
            "5264556173",
            "6141336146",
            "6357385478",
            "4167524645",
            "2176841721",
            "6882881134",
            "4846848554",
            "5283751526"
        )
        assertEquals(1_656, Day11.solveFirst(sampleChangeMe))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(1_615, Day11.solveFirst(input))
    }
}
