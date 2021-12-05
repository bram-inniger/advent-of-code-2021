package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Test {

    private val input = readInputFile("05")
    private val sampleLines = listOf(
        "0,9 -> 5,9",
        "8,0 -> 0,8",
        "9,4 -> 3,4",
        "2,2 -> 2,1",
        "7,0 -> 7,4",
        "6,4 -> 2,0",
        "0,9 -> 2,9",
        "3,4 -> 1,4",
        "0,0 -> 8,8",
        "5,5 -> 8,2"
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(5, Day05.solveFirst(sampleLines))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(7_644, Day05.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(12, Day05.solveSecond(sampleLines))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(18_627, Day05.solveSecond(input))
    }
}
