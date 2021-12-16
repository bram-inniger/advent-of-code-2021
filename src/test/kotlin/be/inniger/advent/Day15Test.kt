package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day15Test {

    private val input = readInputFile("15")
    private val sampleChitons = listOf(
        "1163751742",
        "1381373672",
        "2136511328",
        "3694931569",
        "7463417111",
        "1319128137",
        "1359912421",
        "3125421639",
        "1293138521",
        "2311944581",
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(40, Day15.solveFirst(sampleChitons))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(487, Day15.solveFirst(input))
    }
}
