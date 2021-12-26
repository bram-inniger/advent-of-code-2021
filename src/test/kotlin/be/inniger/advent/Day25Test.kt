package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day25Test {

    private val input = readInputFile("25")
    private val sampleCucumbers = listOf(
        "v...>>.vv>",
        ".vv>>.vv..",
        ">>.>v>...v",
        ">>v>>.>.v.",
        "v>v.vv.v..",
        ">.>>..v...",
        ".vv..>.>v.",
        "v.v..>>v.v",
        "....v..v.>",
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(58, Day25.solveFirst(sampleCucumbers))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(563, Day25.solveFirst(input))
    }
}
