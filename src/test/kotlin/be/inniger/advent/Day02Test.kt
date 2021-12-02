package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test {

    private val input = readInputFile("02")

    @Test
    fun validateFirstSampleInputs() {
        val sampleCommands = listOf(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2",
        )
        assertEquals(150, Day02.solveFirst(sampleCommands))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(1250395, Day02.solveFirst(input))
    }
}
