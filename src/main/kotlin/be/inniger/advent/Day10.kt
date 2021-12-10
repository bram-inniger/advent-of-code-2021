package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day10 {

    private val openers = setOf('(', '[', '{', '<')
    private val closers = setOf(')', ']', '}', '>')
    private val toOpener = mapOf(
        ')' to '(',
        ']' to '[',
        '}' to '{',
        '>' to '<'
    )
    private val scores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1_197,
        '>' to 25_137,
    )

    fun solveFirst(lines: List<String>) =
        lines.sumOf { line -> scoreLine(line.toList()) }

    private tailrec fun scoreLine(line: List<Char>, opened: List<Char> = listOf()): Int =
        when {
            line.isEmpty() -> 0
            openers.contains(line.head()) -> scoreLine(line.tail(), listOf(line.head()) + opened)
            closers.contains(line.head()) -> {
                if (opened.head() == toOpener[line.head()]) scoreLine(line.tail(), opened.tail())
                else scores[line.head()]!!
            }
            else -> error("Couldn't find a valid opener or closer")
        }
}
