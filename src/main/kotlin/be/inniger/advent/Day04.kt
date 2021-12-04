package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day04 {
    private const val GRID_SIZE = 5
    private val splitRegex = """ +""".toRegex()

    fun solveFirst(bingo: List<String>) = getResults(bingo).minByOrNull { it.turn }?.score ?: error("No results found")
    fun solveSecond(bingo: List<String>) = getResults(bingo).maxByOrNull { it.turn }?.score ?: error("No results found")

    private fun getResults(bingo: List<String>): List<Result> {
        val draws = bingo.head().split(",").map { it.toInt() }
        val boards = bingo.tail().chunked(GRID_SIZE + 1).map { parseBoard(it) }

        return boards.map { playBoard(it, draws) }
    }

    private fun parseBoard(board: List<String>): List<List<Int>> =
        board.tail()
            .map { row ->
                row.trim()
                    .split(splitRegex)
                    .map { number -> number.toInt() }
            }

    private tailrec fun playBoard(
        board: List<List<Int>>,
        draws: List<Int>,
        marked: Set<Position> = setOf(),
        turn: Int = 0
    ): Result =
        if (isWinning(marked)) Result(turn - 1, sumUnmarked(board, marked) * draws[turn - 1])
        else playBoard(board, draws, marked + markPositions(draws[turn], board), turn + 1)

    private fun isWinning(marked: Set<Position>) =
        (0 until GRID_SIZE)
            .any { isWinningHorizontally(marked, row = it) || isWinningVertically(marked, col = it) }

    private tailrec fun isWinningHorizontally(marked: Set<Position>, row: Int, col: Int = 0): Boolean =
        when {
            col >= GRID_SIZE -> true
            !marked.contains(Position(row, col)) -> false
            else -> isWinningHorizontally(marked, row, col + 1)
        }

    private tailrec fun isWinningVertically(marked: Set<Position>, row: Int = 0, col: Int): Boolean =
        when {
            row >= GRID_SIZE -> true
            !marked.contains(Position(row, col)) -> false
            else -> isWinningVertically(marked, row + 1, col)
        }

    private fun markPositions(draw: Int, board: List<List<Int>>) =
        (0 until GRID_SIZE)
            .flatMap { row ->
                (0 until GRID_SIZE)
                    .filter { col -> board[row][col] == draw }
                    .map { col -> Position(row, col) }
            }

    private fun sumUnmarked(board: List<List<Int>>, marked: Set<Position>): Int =
        (0 until GRID_SIZE)
            .flatMap { row ->
                (0 until GRID_SIZE)
                    .filter { col -> !marked.contains(Position(row, col)) }
                    .map { col -> board[row][col] }
            }.sum()

    private data class Position(val row: Int, val col: Int)

    private data class Result(val turn: Int, val score: Int)
}
