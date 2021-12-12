package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day12 {

    private const val START = "start"
    private const val END = "end"

    fun solveFirst(connections: List<String>) =
        countPaths(extractNeighbours(connections), START)

    private tailrec fun extractNeighbours(
        connections: List<String>,
        neighbours: Map<String, Set<String>> = mapOf()
    ): Map<String, Set<String>> =
        if (connections.isEmpty()) neighbours
        else {
            val (a, b) = connections.head().split('-')
            val aToB = a to ((neighbours[a] ?: setOf()) + b)
            val bToA = b to ((neighbours[b] ?: setOf()) + a)

            extractNeighbours(connections.tail(), neighbours + aToB + bToA)
        }

    private fun countPaths(neighbours: Map<String, Set<String>>, toVisit: String, visited: Set<String> = setOf()): Int {
        if (toVisit == END) return 1

        val newVisited = if (isSmall(toVisit)) visited + toVisit else visited
        val unvisitedNeighbours = neighbours[toVisit]!! - visited

        return unvisitedNeighbours.sumOf { countPaths(neighbours, it, newVisited) }
    }

    private fun isSmall(cave: String) = cave.any { it.isLowerCase() }
}
