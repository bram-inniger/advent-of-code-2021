package be.inniger.advent

import be.inniger.advent.Day08.Segment.LD
import be.inniger.advent.Day08.Segment.LU
import be.inniger.advent.Day08.Segment.MD
import be.inniger.advent.Day08.Segment.MM
import be.inniger.advent.Day08.Segment.MU
import be.inniger.advent.Day08.Segment.RD
import be.inniger.advent.Day08.Segment.RU
import kotlin.math.pow

object Day08 {

    fun solveFirst(entries: List<String>) =
        entries.map { Entry.of(it) }
            .flatMap { it.output }
            .count { setOf(2, 3, 4, 7).contains(it.length) }

    fun solveSecond(entries: List<String>): Int {
        val permutations = permutations(Segment.values().toSet())
        return entries.map { Entry.of(it) }.sumOf { entryToValue(it, permutations) }
    }

    private fun permutations(toVisit: Set<Segment>, visited: List<Segment> = listOf()): List<List<Segment>> =
        if (toVisit.isEmpty()) listOf(visited)
        else toVisit.flatMap { permutations(toVisit - it, visited + it) }

    private fun entryToValue(entry: Entry, permutations: List<List<Segment>>): Int {
        val solution = permutations.first { permutation ->
            entry.signals.all { signal -> isValidNumber(toSegments(signal, permutation)) }
        }

        return entry.output.indices
            .sumOf { toNumber(toSegments(entry.output[it], solution)) * 10.0.pow(entry.output.lastIndex - it).toInt() }
    }

    private fun toSegments(signal: String, permutation: List<Segment>) =
        signal.map { it - 'a' }.map { permutation[it] }.toSet()

    private fun isValidNumber(segments: Set<Segment>) =
        Digit.values().any { it.segments == segments }

    private fun toNumber(segments: Set<Segment>) =
        Digit.values().first { it.segments == segments }.value

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

    private enum class Segment { LU, LD, MU, MM, MD, RU, RD }

    private enum class Digit(val value: Int, val segments: Set<Segment>) {
        ZERO(0, setOf(LU, LD, MU, MD, RU, RD)),
        ONE(1, setOf(RU, RD)),
        TWO(2, setOf(LD, MU, MM, MD, RU)),
        THREE(3, setOf(MU, MM, MD, RU, RD)),
        FOUR(4, setOf(LU, MM, RU, RD)),
        FIVE(5, setOf(LU, MU, MM, MD, RD)),
        SIX(6, setOf(LU, LD, MU, MM, MD, RD)),
        SEVEN(7, setOf(MU, RU, RD)),
        EIGHT(8, setOf(LU, LD, MU, MM, MD, RU, RD)),
        NINE(9, setOf(LU, MU, MM, MD, RU, RD))
    }
}
