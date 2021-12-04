package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day04 {
    private const val GRID_SIZE = 5

    fun solveFirst(bingo: List<String>): Int {
        val draws = bingo.head().split(",").map { it.toInt() }
        val boards = bingo.tail().chunked(GRID_SIZE + 1).map { Board.of(it) }

        for (draw in draws) {
            for (board in boards) {
                for (row in (0 until GRID_SIZE)) {
                    for (col in (0 until GRID_SIZE)) {
                        if (board.numbers[row][col] == draw) board.marked.add(Position(row, col))
                    }
                }

                if (board.isWinning()) {
                    var sum = 0
                    for (row in (0 until GRID_SIZE)) {
                        for (col in (0 until GRID_SIZE)) {
                            val position = Position(row, col)

                            if (!board.marked.contains(position)) sum += board.numbers[row][col]
                        }
                    }

                    return sum * draw
                }
            }
        }

        error("Should have found a result by now")
    }

    private data class Position(val row: Int, val col: Int)

    private data class Board(val numbers: List<List<Int>>, val marked: MutableSet<Position> = mutableSetOf()) {

        companion object {
            private val regex = """ +""".toRegex()

            fun of(board: List<String>) = Board(board.tail().map { row ->
                row.trim().split(regex).map { number -> number.toInt() }
            })
        }

        fun isWinning(): Boolean {
            for (i in (0 until GRID_SIZE)) {
                var horizontalMatches = 0
                var verticalMatches = 0

                for (j in (0 until GRID_SIZE)) {
                    val horizontalPosition = Position(i, j)
                    val verticalPosition = Position(j, i)

                    if (marked.contains(horizontalPosition)) horizontalMatches++
                    if (marked.contains(verticalPosition)) verticalMatches++
                }

                if (horizontalMatches == GRID_SIZE || verticalMatches == GRID_SIZE) return true
            }

            return false
        }
    }
}
