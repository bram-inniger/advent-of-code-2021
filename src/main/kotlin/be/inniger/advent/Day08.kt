package be.inniger.advent

object Day08 {

    fun solveFirst(entries: List<String>) =
        entries.map { Entry.of(it) }
            .flatMap { it.output }
            .count { setOf(2, 3, 4, 7).contains(it.length) }

    private data class Entry(val signals: List<String>, val output: List<String>) {
        companion object {
            private val regex = """^((?:\w{2,7} ){10})\|((?: \w{2,7}){4})$""".toRegex()

            fun of(entry: String): Entry {
                val (signals, output) = regex.find(entry)!!.destructured
                return Entry(
                    signals.trim().split(' '),
                    output.trim().split(' '),
                )
            }
        }
    }
}
