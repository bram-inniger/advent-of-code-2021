package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Test {

    private val input = readInputFile("14").joinToString("\n")
    private val sampleManual =
        """
            NNCB

            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C
        """.trimIndent()

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(1_588L, Day14.solveFirst(sampleManual))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(3_411L, Day14.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(2_188_189_693_529L, Day14.solveSecond(sampleManual))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(7_477_815_755_570L, Day14.solveSecond(input))
    }
}
