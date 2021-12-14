package be.inniger.advent

object Day14 {

    fun solveFirst(manual: String): Long {
        val (template, rulesText) = manual.split("\n\n")

        val pairs = parsePairs(template)
        val rules = parseRules(rulesText)
        val count = countComponents(step(10, pairs, rules))

        return count.maxOf { it.value } - count.minOf { it.value }
    }

    private fun parsePairs(template: String) =
        (0 until template.lastIndex)
            .map { "${template[it]}${template[it + 1]}" to 1L }
            .groupSumming()

    private fun parseRules(rules: String) =
        rules.split('\n')
            .map { it.split(" -> ") }
            .associate { it[0] to listOf("${it[0][0]}${it[1]}", "${it[1]}${it[0][1]}") }

    private tailrec fun step(
        nrSteps: Int,
        polymer: Map<String, Long>,
        rules: Map<String, List<String>>
    ): Map<String, Long> =
        if (nrSteps == 0) polymer
        else {
            val newPolymer = polymer.flatMap {
                val pair = it.key
                val count = it.value
                val rule = rules[pair] ?: listOf(pair)

                rule.map { newPair -> newPair to count }
            }
                .groupSumming()

            step(nrSteps - 1, newPolymer, rules)
        }

    private fun countComponents(polymer: Map<String, Long>) =
        polymer.flatMap {
            val pair = it.key
            val count = it.value

            listOf(pair[0] to count, pair[1] to count)
        }
            .groupSumming()
            .mapValues { (it.value + 1) / 2 }

    private fun <T> List<Pair<T, Long>>.groupSumming() =
        this.groupBy { it.first }
            .mapValues { (it.value.sumOf { count -> count.second }) }
}
