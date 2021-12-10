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
    private val openerScores = mapOf(
        '(' to 1L,
        '[' to 2L,
        '{' to 3L,
        '<' to 4L,
    )
    private val closerScores = mapOf(
        ')' to 3L,
        ']' to 57L,
        '}' to 1_197L,
        '>' to 25_137L,
    )

    fun solveFirst(lines: List<String>) = scoreLines(lines, LineStatus.CORRUPTED).sum()
    fun solveSecond(lines: List<String>) = scoreLines(lines, LineStatus.INCOMPLETE).sorted().let { it[it.size / 2] }

    private fun scoreLines(lines: List<String>, status: LineStatus) =
        lines.map { line -> scoreLine(line.toList()) }
            .filter { it.status == status }
            .map { it.score }

    @Suppress("KotlinConstantConditions")
    private tailrec fun scoreLine(line: List<Char>, opened: List<Char> = listOf()): LineResult =
        when {
            line.isEmpty() && opened.isEmpty() -> LineResult(LineStatus.COMPLETE, 0L)
            line.isEmpty() && opened.isNotEmpty() -> LineResult(LineStatus.INCOMPLETE, scoreOpened(opened))
            openers.contains(line.head()) -> scoreLine(line.tail(), listOf(line.head()) + opened)
            closers.contains(line.head()) -> {
                if (opened.head() == toOpener[line.head()]) scoreLine(line.tail(), opened.tail())
                else LineResult(LineStatus.CORRUPTED, closerScores[line.head()]!!)
            }
            else -> error("Couldn't find a valid opener or closer")
        }

    private tailrec fun scoreOpened(opened: List<Char>, score: Long = 0L): Long =
        if (opened.isEmpty()) score
        else scoreOpened(opened.tail(), score * 5 + openerScores[opened.head()]!!)

    private enum class LineStatus { COMPLETE, INCOMPLETE, CORRUPTED }

    private data class LineResult(val status: LineStatus, val score: Long)
}
