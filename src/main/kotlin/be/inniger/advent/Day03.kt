package be.inniger.advent

object Day03 {

    fun solveFirst(diagnostics: List<String>): Int {
        val gamma = diagnostics.first().indices.map { mostCommonValue(diagnostics, it) }
        val delta = gamma.map { if (it == '1') '0' else '1' }

        return gamma.asInt() * delta.asInt()
    }

    fun solveSecond(diagnostics: List<String>): Int {
        val oxygen = determineRating(diagnostics, { diagnosticDigit, common -> diagnosticDigit == common })
        val co2 = determineRating(diagnostics, { diagnosticDigit, common -> diagnosticDigit != common })

        return oxygen * co2
    }

    private tailrec fun determineRating(
        diagnostics: List<String>, predicate: (Any, Any) -> Boolean, position: Int = 0
    ): Int = if (diagnostics.size <= 1) diagnostics.single().toInt(2)
    else {
        val common = mostCommonValue(diagnostics, position)
        val newDiagnostics = diagnostics.filter { predicate(it[position], common) }

        determineRating(newDiagnostics, predicate, position + 1)
    }

    private fun mostCommonValue(diagnostics: List<String>, position: Int) =
        diagnostics.map { it[position] }.count { it == '1' }.let { if (it * 2 >= diagnostics.size) '1' else '0' }

    private fun List<Char>.asInt() = this.joinToString("").toInt(2)
}
