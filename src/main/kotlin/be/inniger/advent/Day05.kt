package be.inniger.advent

import kotlin.math.abs
import kotlin.math.max

object Day05 {

    fun solveFirst(lines: List<String>) =
        countOverlaps(lines.map { Line.of(it) }.filter { it.x1 == it.x2 || it.y1 == it.y2 })

    fun solveSecond(lines: List<String>) =
        countOverlaps(lines.map { Line.of(it) })

    private fun countOverlaps(lines: List<Line>) =
        lines.flatMap { toCoordinates(it) }
            .groupingBy { it }
            .eachCount()
            .filter { it.value >= 2 }
            .count()

    private fun toCoordinates(line: Line): List<Coordinate> {
        val xCoefficient = if (line.x1 == line.x2) 0 else (line.x2 - line.x1) / abs(line.x2 - line.x1)
        val yCoefficient = if (line.y1 == line.y2) 0 else (line.y2 - line.y1) / abs(line.y2 - line.y1)
        val delta = max(abs(line.x2 - line.x1), abs(line.y2 - line.y1))

        return (0..delta).map { Coordinate(line.x1 + it * xCoefficient, line.y1 + it * yCoefficient) }
    }

    private data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        companion object {
            private val regex = """^(\d+),(\d+) -> (\d+),(\d+)$""".toRegex()

            fun of(line: String): Line {
                val (x1, y1, x2, y2) = regex.find(line)!!.destructured
                return Line(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
            }
        }
    }

    private data class Coordinate(val x: Int, val y: Int)
}
