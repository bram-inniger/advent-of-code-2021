package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {

    private val input = readInputFile("10")
    private val sampleLines = listOf(
        "[({(<(())[]>[[{[]{<()<>>",
        "[(()[<>])]({[<{<<[]>>(",
        "{([(<{}[<>[]}>{[]{[(<()>",
        "(((({<>}<{<{<>}{[]{[]{}",
        "[[<[([]))<([[{}[[()]]]",
        "[{[{({}]{}}([{[{{{}}([]",
        "{<[[]]>}<{[{[{[]{()[[[]",
        "[<(<(<(<{}))><([]([]()",
        "<{([([[(<>()){}]>(<<{{",
        "<{([{{}}[<[[[<>{}]]]>[]]"
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(26_397L, Day10.solveFirst(sampleLines))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(271_245L, Day10.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(288_957L, Day10.solveSecond(sampleLines))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(1_685_293_086L, Day10.solveSecond(input))
    }
}
