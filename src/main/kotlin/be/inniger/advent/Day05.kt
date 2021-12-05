package be.inniger.advent

import kotlin.math.max
import kotlin.math.min

object Day05 {

    fun solveFirst(lines: List<String>) =
        lines.map { Line.of(it) }
            .flatMap { toCoordinates(it) }
            .groupingBy { it }
            .eachCount()
            .filter { it.value >= 2 }
            .count()

    private fun toCoordinates(line: Line): List<Coordinate> =
        when {
            line.x1 == line.x2 -> (line.y1..line.y2).map { y -> Coordinate(line.x1, y) }
            line.y1 == line.y2 -> (line.x1..line.x2).map { x -> Coordinate(x, line.y1) }
            else -> listOf() // ignoring lines that aren't horizontal or vertical
        }

    private data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        companion object {
            private val regex = """^(\d+),(\d+) -> (\d+),(\d+)$""".toRegex()

            fun of(line: String): Line {
                val (x1, y1, x2, y2) = regex.find(line)!!.destructured
                return Line(
                    min(x1.toInt(), x2.toInt()),
                    min(y1.toInt(), y2.toInt()),
                    max(x1.toInt(), x2.toInt()),
                    max(y1.toInt(), y2.toInt())
                )
            }
        }
    }

    private data class Coordinate(val x: Int, val y: Int)
}
