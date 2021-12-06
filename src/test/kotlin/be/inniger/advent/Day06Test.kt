package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {

    private val input = readInputFile("06")[0].split(',').map { it.toInt() }
    private val sampleLanternFish = listOf(3, 4, 3, 1, 2)

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(5_934L, Day06.solveFirst(sampleLanternFish))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(385_391L, Day06.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(26_984_457_539L, Day06.solveSecond(sampleLanternFish))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(1_728_611_055_389L, Day06.solveSecond(input))
    }
}
