package be.inniger.advent

object Day06 {

    fun solveFirst(lanternFish: List<Int>) = countFish(80, lanternFish)
    fun solveSecond(lanternFish: List<Int>) = countFish(256, lanternFish)

    private fun countFish(nrDays: Int, lanternFish: List<Int>) =
        simulate(nrDays, lanternFish.groupingBy { it }.eachCount().mapValues { it.value.toLong() })
            .values
            .sum()

    private tailrec fun simulate(nrDays: Int, lanternFish: Map<Int, Long>): Map<Int, Long> =
        if (nrDays == 0) lanternFish
        else simulate(
            nrDays - 1,
            mapOf(
                0 to (lanternFish[1] ?: 0L),
                1 to (lanternFish[2] ?: 0L),
                2 to (lanternFish[3] ?: 0L),
                3 to (lanternFish[4] ?: 0L),
                4 to (lanternFish[5] ?: 0L),
                5 to (lanternFish[6] ?: 0L),
                6 to ((lanternFish[7] ?: 0L) + (lanternFish[0] ?: 0L)),
                7 to (lanternFish[8] ?: 0L),
                8 to (lanternFish[0] ?: 0L)
            )
        )
}
