package be.inniger.advent

object Day15 {

    private const val REPEATS = 5
    private const val MAX_ELEMENT = 9

    fun solveFirst(chitons: List<String>) = dijkstra(setZero(toGrid(chitons)))
    fun solveSecond(chitons: List<String>) = dijkstra(setZero(extend(toGrid(chitons))))

    private fun toGrid(chitons: List<String>) =
        chitons.mapIndexed { ver, line ->
            line.mapIndexed { hor, chiton ->
                Node(Position(ver, hor), chiton.digitToInt(), Int.MAX_VALUE)
            }
        }

    private fun extend(grid: List<List<Node>>): List<List<Node>> {
        val verSize = grid.size
        val horSize = grid.first().size

        return (0 until verSize * REPEATS).map { ver ->
            (0 until horSize * REPEATS).map { hor ->
                Node(
                    Position(ver, hor),
                    ((grid[ver % verSize][hor % horSize].chiton + hor / horSize + ver / verSize) - 1) % MAX_ELEMENT + 1,
                    Int.MAX_VALUE
                )
            }
        }
    }

    private fun setZero(grid: List<List<Node>>) =
        grid.map { row ->
            row.map {
                if (it.position.hor == 0 && it.position.ver == 0) it.copy(tentativeDistance = 0) else it
            }
        }

    // FIXME, mutability, ewww
    private fun dijkstra(immutableGrid: List<List<Node>>): Int {
        val grid = immutableGrid.map { it.toMutableList() }
        val unvisited = grid.flatten().toSortedSet(compareBy<Node> { it.tentativeDistance }.thenBy { it.position })

        val maxVer = grid.lastIndex
        val maxHor = grid.first().lastIndex
        val endPosition = Position(maxVer, maxHor)

        val unvisitedNeighbours: (Position) -> List<Node> =
            { position -> unvisitedNeighboursHelper(position, maxVer, maxHor, unvisited, grid) }

        while (true) {
            val current = unvisited.first()
            unvisited.remove(current)

            if (current.position == endPosition) return current.tentativeDistance

            for (neighbour in unvisitedNeighbours(current.position)) {
                val tentativeDistance = neighbour.tentativeDistance
                val proposedNewDistance = current.tentativeDistance + neighbour.chiton

                if (proposedNewDistance < tentativeDistance) {
                    val newNeighbour = neighbour.copy(tentativeDistance = proposedNewDistance)

                    unvisited.remove(neighbour)
                    unvisited.add(newNeighbour)

                    grid[neighbour.position.ver][neighbour.position.hor] = newNeighbour
                }
            }
        }
    }

    private fun unvisitedNeighboursHelper(
        p: Position, maxVer: Int, maxHor: Int, unvisited: Set<Node>, grid: List<List<Node>>
    ) =
        listOfNotNull(
            if (p.ver - 1 >= 0 && unvisited.contains(grid[p.ver - 1][p.hor])) grid[p.ver - 1][p.hor] else null,
            if (p.ver + 1 <= maxVer && unvisited.contains(grid[p.ver + 1][p.hor])) grid[p.ver + 1][p.hor] else null,
            if (p.hor - 1 >= 0 && unvisited.contains(grid[p.ver][p.hor - 1])) grid[p.ver][p.hor - 1] else null,
            if (p.hor + 1 <= maxHor && unvisited.contains(grid[p.ver][p.hor + 1])) grid[p.ver][p.hor + 1] else null
        )

    private data class Position(val ver: Int, val hor: Int) : Comparable<Position> {
        override fun compareTo(other: Position) = compareValuesBy(this, other, { it.ver }, { it.hor })
    }

    private data class Node(val position: Position, val chiton: Int, val tentativeDistance: Int)
}
