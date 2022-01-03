package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day24Test {

    private val input = readInputFile("24")

    @Test
    fun validateFirstSampleInputs() {
        // No test inputs
    }

    @Test
    fun validateFirstSolution() {
        assertEquals("39494195799979", Day24.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        // No test inputs
    }

    @Test
    fun validateSecondSolution() {
        assertEquals("13161151139617", Day24.solveSecond(input))
    }
}
