package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day20Test {

    private val input = readInputFile("20").joinToString("\n")
    private val sampleScanner =
        """
            ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#

            #..#.
            #....
            ##..#
            ..#..
            ..###
        """.trimIndent()

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(35, Day20.solveFirst(sampleScanner))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(5_432, Day20.solveFirst(input))
    }
}
