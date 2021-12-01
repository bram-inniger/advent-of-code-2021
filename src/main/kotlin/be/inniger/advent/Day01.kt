package be.inniger.advent

object Day01 {

    fun solveFirst(measurements: List<Int>) =
        (1..measurements.lastIndex).count { measurements[it] > measurements[it - 1] }
}
