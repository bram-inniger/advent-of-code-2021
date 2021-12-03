package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {

    private val input = readInputFile("03")

    @Test
    fun validateFirstSampleInputs() {
        val sampleDiagnostics = listOf(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010",
        )
        assertEquals(198, Day03.solveFirst(sampleDiagnostics))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(4_174_964, Day03.solveFirst(input))
    }
}
