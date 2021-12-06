package be.inniger.advent

object Day06 {

    fun solveFirst(lanternFish: List<Int>) =
        simulate(80, lanternFish.groupingBy { it }.eachCount().mapValues { it.value.toLong() })
            .values
            .sum()

    private tailrec fun simulate(nrDays: Int, lanternFish: Map<Int, Long>): Map<Int, Long> =
        if (nrDays == 0) lanternFish
        else simulate(
            nrDays - 1,
            mapOf(
                0 to (lanternFish[1] ?: 0),
                1 to (lanternFish[2] ?: 0),
                2 to (lanternFish[3] ?: 0),
                3 to (lanternFish[4] ?: 0),
                4 to (lanternFish[5] ?: 0),
                5 to (lanternFish[6] ?: 0),
                6 to ((lanternFish[7] ?: 0) + (lanternFish[0] ?: 0)),
                7 to (lanternFish[8] ?: 0),
                8 to (lanternFish[0] ?: 0)
            )
        )
}
