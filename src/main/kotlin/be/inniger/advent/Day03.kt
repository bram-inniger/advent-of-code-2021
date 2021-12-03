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

    fun solveSecond(diagnostics: List<String>): Int {
        val oxygen = determineRatingRec(diagnostics, { diagnosticDigit, common -> diagnosticDigit == common })
        val co2 = determineRatingRec(diagnostics, { diagnosticDigit, common -> diagnosticDigit != common })

        return oxygen * co2
    }

    private fun readPower(power: List<Int>, reversed: Boolean) = power.map {
        when {
            (if (reversed) -it else it) > 0 -> '0'
            (if (reversed) -it else it) < 0 -> '1'
            else -> error("Even amounts of 0's and 1's in the input")
        }
    }.joinToString("").toInt(2)

    private tailrec fun determineRatingRec(
        diagnostics: List<String>, predicate: (Char, Char) -> Boolean, position: Int = 0
    ): Int = if (diagnostics.size <= 1) diagnostics.single().toInt(2)
    else {
        val common = mostCommonValue(diagnostics, position)
        val newDiagnostics = diagnostics.filter { predicate(it[position], common) }

        determineRatingRec(newDiagnostics, predicate, position + 1)
    }

    private fun mostCommonValue(diagnostics: List<String>, position: Int): Char {
        val frequencyMap = diagnostics.map { it[position] }.groupingBy { it }.eachCount()

        return if (frequencyMap['0']!! > frequencyMap['1']!!) '0' else '1'
    }
}
