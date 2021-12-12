package be.inniger.advent

private typealias Node = String
private typealias Graph = Map<Node, Set<Node>>

object Day12 {

    private const val START = "start"
    private const val END = "end"

    fun solveFirst(connections: List<String>) = countPaths(neighbours(connections), START, twice = false)
    fun solveSecond(connections: List<String>) = countPaths(neighbours(connections), START, twice = true)

    private fun neighbours(connections: List<String>) =
        connections.map { it.split('-') }
            .flatMap { listOf(it.first() to it.last(), it.last() to it.first()) }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.toSet() }

    private fun countPaths(graph: Graph, toVisit: Node, visited: Set<Node> = setOf(), twice: Boolean): Int {
        if (toVisit == END) return 1

        val newTwice = twice && (!visited.contains(toVisit) || toVisit == START)
        val newVisited = if (isSmall(toVisit)) visited + toVisit else visited
        val unvisitedNeighbours = graph[toVisit]!! - (if (newTwice) setOf(START) else visited)

        return unvisitedNeighbours.sumOf { countPaths(graph, it, newVisited, newTwice) }
    }

    private fun isSmall(cave: Node) = cave.any { it.isLowerCase() }
}
