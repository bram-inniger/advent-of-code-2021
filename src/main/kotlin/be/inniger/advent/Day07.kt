package be.inniger.advent

import kotlin.math.abs
import kotlin.math.floor

object Day07 {

    fun solveFirst(crabs: List<Int>): Int {
        val median = crabs.sorted().let {
            if (crabs.size % 2 != 0) it[crabs.size / 2]
            else (it[crabs.size / 2 - 1] + it[crabs.size / 2]) / 2
        }

        return crabs.sumOf { abs(it - median) }
    }

    fun solveSecond(crabs: List<Int>): Int {
        val averageFloor = floor(crabs.average()).toInt()
        val averageCeil = averageFloor + 1

        return listOf(averageFloor, averageCeil)
            .minOf { average ->
                crabs.sumOf { crab -> getFuelBetween(crab, average) }
            }
    }

    private fun getFuelBetween(a: Int, b: Int) =
        abs(b - a) * (abs(b - a) + 1) / 2
}
