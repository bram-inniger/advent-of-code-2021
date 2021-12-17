package be.inniger.advent

import kotlin.math.max

object Day17 {

    fun solveFirst(target: String) = calculatePaths(target).maxOf { it.maxY }
    fun solveSecond(target: String) = calculatePaths(target).count()

    private fun calculatePaths(target: String): List<Path> {
        val area = Area.of(target)

        return (1..area.xRange.last).flatMap { vX ->
            (area.yRange.first..-area.yRange.first).map { vy ->
                calculatePath(area, vX, vy)
            }
        }
            .filter { it.trajectory == Trajectory.HIT }
    }

    private tailrec fun calculatePath(area: Area, vX: Int, vY: Int, x: Int = 0, y: Int = 0, maxY: Int = 0): Path =
        when {
            x in area.xRange && y in area.yRange -> Path(Trajectory.HIT, maxY)
            x > area.xRange.last -> Path(Trajectory.OVERSHOOT, Int.MIN_VALUE)
            y < area.yRange.first -> Path(Trajectory.UNDERSHOOT, Int.MIN_VALUE)
            else -> calculatePath(
                area,
                if (vX == 0) 0 else vX - 1,
                vY - 1,
                x + vX,
                y + vY,
                max(maxY, y)
            )
        }

    private enum class Trajectory { UNDERSHOOT, HIT, OVERSHOOT }

    private data class Area(val xRange: IntRange, val yRange: IntRange) {
        companion object {
            private val regex = """^target area: x=(-?\d+)\.\.(-?\d+), y=(-?\d+)\.\.(-?\d+)$""".toRegex()

            fun of(target: String): Area {
                val (xMin, xMax, yMin, yMax) = regex.find(target)!!.destructured
                return Area(xMin.toInt()..xMax.toInt(), yMin.toInt()..yMax.toInt())
            }
        }
    }

    private data class Path(val trajectory: Trajectory, val maxY: Int)
}
