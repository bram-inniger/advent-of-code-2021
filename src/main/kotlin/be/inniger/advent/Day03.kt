package be.inniger.advent

object Day03 {

    fun solveFirst(diagnostics: List<String>): Int {
        val power = diagnostics.map { diagnostic ->
            diagnostic.map { reading ->
                when (reading) {
                    '1' -> 1
                    '0' -> -1
                    else -> error("Invalid input, expecting only 0 or 1")
                }
            }
        }.reduceRight { el, acc -> acc.zip(el) { a, b -> a + b } }

        val gamma = readPower(power, reversed = false)
        val delta = readPower(power, reversed = true)

        return gamma * delta
    }

    private fun readPower(power: List<Int>, reversed: Boolean) = power.map {
        when {
            (if (reversed) -it else it) > 0 -> '0'
            (if (reversed) -it else it) < 0 -> '1'
            else -> error("Even amounts of 0's and 1's in the input")
        }
    }.joinToString("").toInt(2)
}
