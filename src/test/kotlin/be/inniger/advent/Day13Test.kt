package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {

    private val input = readInputFile("13").joinToString("\n")
    private val sampleManual =
        """
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0

            fold along y=7
            fold along x=5
        """.trimIndent()

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(17, Day13.solveFirst(sampleManual))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(807, Day13.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        val code =
            """
                #####
                #...#
                #...#
                #...#
                #####
            """.trimIndent()
        assertEquals(code, Day13.solveSecond(sampleManual))
    }

    @Test
    fun validateSecondSolution() {
        val code =
            """
                #.....##..#..#.####..##..#..#.####...##
                #....#..#.#..#.#....#..#.#..#.#.......#
                #....#....####.###..#....#..#.###.....#
                #....#.##.#..#.#....#.##.#..#.#.......#
                #....#..#.#..#.#....#..#.#..#.#....#..#
                ####..###.#..#.####..###..##..####..##.
            """.trimIndent()
        assertEquals(code, Day13.solveSecond(input))
    }
}
