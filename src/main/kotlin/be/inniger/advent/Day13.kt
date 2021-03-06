package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day13 {

    fun solveFirst(manual: String) = foldManual(manual, once = true).count()
    fun solveSecond(manual: String) = foldManual(manual, once = false).print()

    private fun foldManual(manual: String, once: Boolean): Set<Point> {
        val (pointsText, instructionsText) = manual.split("\n\n")

        val points = readPoints(pointsText)
        val instructions = instructionsText.split('\n')
            .map { Instruction.of(it) }
            .let { if (once) listOf(it.first()) else it }

        return fold(instructions, points)
    }

    private fun readPoints(points: String) =
        points.split('\n')
            .map { it.split(',') }
            .map { Point(it.first().toInt(), it.last().toInt()) }
            .toSet()

    private tailrec fun fold(instructions: List<Instruction>, points: Set<Point>): Set<Point> =
        if (instructions.isEmpty()) points
        else {
            val instruction = instructions.head()
            val newPoints = when (instruction.direction) {
                Direction.HORIZONTAL -> points.map {
                    if (it.x < instruction.location) it else Point(it.x - 2 * (it.x - instruction.location), it.y)
                }
                Direction.VERTICAL -> points.map {
                    if (it.y < instruction.location) it else Point(it.x, it.y - 2 * (it.y - instruction.location))
                }
            }.toSet()

            fold(instructions.tail(), newPoints)
        }

    private fun Set<Point>.print() =
        (0..this.maxOf { it.y })
            .joinToString("\n") { y ->
                (0..this.maxOf { it.x })
                    .map { x -> if (this.contains(Point(x, y))) '#' else '.' }
                    .joinToString("")
            }

    private enum class Direction { HORIZONTAL, VERTICAL }

    private data class Point(val x: Int, val y: Int)

    private data class Instruction(val direction: Direction, val location: Int) {
        companion object {
            private val regex = """^fold along ([xy])=(\d+)$""".toRegex()

            fun of(instruction: String): Instruction {
                val (direction, location) = regex.find(instruction)!!.destructured
                return Instruction(
                    when (direction) {
                        "x" -> Direction.HORIZONTAL
                        "y" -> Direction.VERTICAL
                        else -> error("Invalid direction: $direction")
                    },
                    location.toInt()
                )
            }
        }
    }
}
