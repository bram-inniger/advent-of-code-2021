package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {

    private val input = readInputFile("12")
    private val smallSampleConnections = listOf(
        "start-A",
        "start-b",
        "A-c",
        "A-b",
        "b-d",
        "A-end",
        "b-end"
    )
    private val midSampleConnections = listOf(
        "dc-end",
        "HN-start",
        "start-kj",
        "dc-start",
        "dc-HN",
        "LN-dc",
        "HN-end",
        "kj-sa",
        "kj-HN",
        "kj-dc"
    )
    private val largeSampleConnections = listOf(
        "fs-end",
        "he-DX",
        "fs-he",
        "start-DX",
        "pj-DX",
        "end-zg",
        "zg-sl",
        "zg-pj",
        "pj-he",
        "RW-he",
        "fs-DX",
        "pj-RW",
        "zg-RW",
        "start-pj",
        "he-WI",
        "zg-he",
        "pj-fs",
        "start-RW"
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(10, Day12.solveFirst(smallSampleConnections))
        assertEquals(19, Day12.solveFirst(midSampleConnections))
        assertEquals(226, Day12.solveFirst(largeSampleConnections))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(4_885, Day12.solveFirst(input))
    }
}
