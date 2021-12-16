package be.inniger.advent

import java.util.SortedSet

object Day15 {

    // FIXME, mutability, ewww
    fun solveFirst(chitons: List<String>): Int {
        val grid = chitons.mapIndexed { ver, line ->
            line.mapIndexed { hor, chiton ->
                Node(
                    Position(ver, hor),
                    chiton.digitToInt(),
                    if (ver == 0 && hor == 0) 0 else Int.MAX_VALUE
                )
            }.toMutableList()
        }
        val unvisited = grid.flatten().toSortedSet(compareBy<Node> { it.tentativeDistance }.thenBy { it.position })

        val maxVer = grid.lastIndex
        val maxHor = grid.first().lastIndex

        val unvisitedNeighbours: (Position) -> List<Node> =
            { position -> unvisitedNeighboursHelper(position, maxVer, maxHor, unvisited, grid) }

        while (unvisited.isNotEmpty()) {
           val current = unvisited.first()
           unvisited.remove(current)

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

        return grid[maxVer][maxHor].tentativeDistance
    }

    // FIXME, mutability, ewww
    private fun unvisitedNeighboursHelper(
        position: Position,
        maxVer: Int,
        maxHor: Int,
        unvisited: SortedSet<Node>,
        grid: List<List<Node>>
    ): List<Node> {
        val neighbours = mutableListOf<Node>()
        val ver = position.ver
        val hor = position.hor

        if (ver - 1 >= 0 && unvisited.contains(grid[ver - 1][hor])) neighbours.add(grid[ver - 1][hor])
        if (ver + 1 <= maxVer && unvisited.contains(grid[ver + 1][hor])) neighbours.add(grid[ver + 1][hor])
        if (hor - 1 >= 0 && unvisited.contains(grid[ver][hor - 1])) neighbours.add(grid[ver][hor - 1])
        if (hor + 1 <= maxHor && unvisited.contains(grid[ver][hor + 1])) neighbours.add(grid[ver][hor + 1])

        return neighbours
    }

    private data class Position(val ver: Int, val hor: Int) : Comparable<Position> {
        override fun compareTo(other: Position) = compareValuesBy(this, other, { it.ver }, { it.hor })
    }

    private data class Node(val position: Position, val chiton: Int, val tentativeDistance: Int)
}
