package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day18 {

    private const val EXPLODE = 5

    private val leftOpenRegex = """(\d+]|\d+\[)""".toRegex()
    private val rightOpenRegex = """(\[\d+|]\d+)""".toRegex()
    private val literalRegex = """^(\d+)$""".toRegex()
    private val tooBigRegex = """(\d{2,})""".toRegex()
    private val constantPairRegex = """^\[(\d+),(\d+)]$""".toRegex()
    private val commaRegex = """,""".toRegex()

    fun solveFirst(numbers: List<String>) =
        magnitude(sum(numbers))

    fun solveSecond(numbers: List<String>) =
        numbers.flatMap { first ->
            numbers.filter { second -> second != first }
                .map { second -> listOf(first, second) }
        }
            .map { sum(it) }
            .maxOf { magnitude(it) }

    private tailrec fun sum(numbers: List<String>): String =
        if (numbers.size == 1) numbers.single()
        else {
            val a = numbers.head()
            val b = numbers.tail().head()
            val sum = reduce(combine(a, b))
            val newNumbers = listOf(sum) + numbers.tail().tail()

            sum(newNumbers)
        }

    private fun combine(a: String, b: String) = "[$a,$b]"

    private tailrec fun reduce(number: String): String =
        when {
            shouldExplode(number) -> reduce(explode(number))
            shouldSplit(number) -> reduce(split(number))
            else -> number
        }

    private tailrec fun shouldExplode(number: String, nested: Int = 0): Boolean =
        when {
            number.isEmpty() -> false
            nested == EXPLODE -> true
            number.head() == '[' -> shouldExplode(number.tail(), nested + 1)
            number.head() == ']' -> shouldExplode(number.tail(), nested - 1)
            else -> shouldExplode(number.tail(), nested)
        }

    private fun explode(number: String): String {
        val start = findExplode(number)
        val end = findClosing(number.substring(start)) + start

        val (left, right) = constantPairRegex.find(number.substring(start..end))!!
            .groupValues
            .tail()
            .map { it.toInt() }

        val leftLitOffset = findNearestLiteral(number.substring(0, start).reversed())
        val rightLitOffset = findNearestLiteral(number.substring(end + 1))

        val leftSub = if (leftLitOffset == null) number.substring(0, start)
        else {
            val leftPart = number.substring(0, start - leftLitOffset.last - 1)
            val middle = number.substring(start - leftLitOffset.last - 1, start - leftLitOffset.first).toInt() + left
            val rightPart = number.substring(start - leftLitOffset.first, start)
            "$leftPart$middle$rightPart"
        }
        val rightSub = if (rightLitOffset == null) number.substring(end + 1)
        else {
            val leftPart = number.substring(end + 1, end + rightLitOffset.first + 1)
            val middle = number.substring(end + rightLitOffset.first + 1, end + rightLitOffset.last + 2).toInt() + right
            val rightPart = number.substring(end + rightLitOffset.last + 2)
            "$leftPart$middle$rightPart"
        }

        return "${leftSub}0$rightSub"
    }

    private tailrec fun findExplode(number: String, nested: Int = 0, position: Int = -1): Int =
        when {
            nested == EXPLODE -> position
            number.head() == '[' -> findExplode(number.tail(), nested + 1, position + 1)
            number.head() == ']' -> findExplode(number.tail(), nested - 1, position + 1)
            else -> findExplode(number.tail(), nested, position + 1)
        }

    private tailrec fun findClosing(number: String, position: Int = 0, nrOpen: Int = 0): Int =
        when {
            number.head() == '[' -> findClosing(number.tail(), position + 1, nrOpen + 1)
            number.head() == ']' && nrOpen == 1 -> position
            number.head() == ']' -> findClosing(number.tail(), position + 1, nrOpen - 1)
            else -> findClosing(number.tail(), position + 1, nrOpen)
        }

    private fun findNearestLiteral(number: String): IntRange? {
        val right = leftOpenRegex.find(number)?.groups?.get(1)?.range?.let { it.first until it.last }
        val left = rightOpenRegex.find(number)?.groups?.get(1)?.range?.let { it.first + 1..it.last }

        return listOfNotNull(left, right).minByOrNull { it.first }
    }

    private fun shouldSplit(number: String) = tooBigRegex.containsMatchIn(number)

    private fun split(number: String): String {
        val location = tooBigRegex.find(number)!!.groups[1]!!.range
        val toSplit = number.substring(location).toInt()

        val left = toSplit / 2
        val right = toSplit - left

        return number.substring(0, location.first) + "[$left,$right]" + number.substring(location.last + 1)
    }

    private fun magnitude(number: String): Int =
        if (literalRegex.matches(number)) number.toInt()
        else {
            val left = if (number[1] == '[') {
                val closing = findClosing(number.substring(1)) + 2
                number.substring(1, closing)
            } else {
                val comma = commaRegex.find(number)!!.groups[0]!!.range.first
                number.substring(1, comma)
            }

            val right = number.substring(left.length + 2, number.lastIndex)

            val leftMag = magnitude(left)
            val rightMag = magnitude(right)

            3 * leftMag + 2 * rightMag
        }
}
