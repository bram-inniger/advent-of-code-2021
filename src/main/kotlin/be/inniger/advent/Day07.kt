package be.inniger.advent

import kotlin.math.abs

object Day07 {

    fun solveFirst(crabs: List<Int>): Int {
        val median = crabs.sorted().let {
            if (crabs.size % 2 != 0) it[crabs.size / 2]
            else (it[crabs.size / 2 - 1] + it[crabs.size / 2]) / 2
        }

        return crabs.sumOf { abs(it - median) }
    }
}
