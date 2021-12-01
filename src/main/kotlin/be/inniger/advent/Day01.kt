package be.inniger.advent

object Day01 {

    fun solveFirst(measurements: List<Int>) = countIncreases(measurements, 1)
    fun solveSecond(measurements: List<Int>) = countIncreases(measurements, 3)

    private fun countIncreases(measurements: List<Int>, indexJump: Int) =
        (indexJump..measurements.lastIndex).count { measurements[it] > measurements[it - indexJump] }
}
