package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {

    private val input = readInputFile("21")
    private val samplePositions = listOf(
        "Player 1 starting position: 4",
        "Player 2 starting position: 8",
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(739_785, Day21.solveFirst(samplePositions))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(684_495, Day21.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(444_356_092_776_315L, Day21.solveSecond(samplePositions))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(152_587_196_649_184L, Day21.solveSecond(input))
    }
}
