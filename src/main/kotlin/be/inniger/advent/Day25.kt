package be.inniger.advent

object Day25 {

    fun solveFirst(cucumbers: List<String>) =
        moveAll(
            east = Group(parse(cucumbers, '>')),
            south = Group(parse(cucumbers, 'v')),
            width = cucumbers.first().length,
            height = cucumbers.size
        )

    private fun parse(cucumbers: List<String>, type: Char) =
        cucumbers.indices.flatMap { ver ->
            cucumbers.first().indices
                .filter { hor -> cucumbers[ver][hor] == type }
                .map { hor -> Position(ver, hor) }
        }.toSet()

    private tailrec fun moveAll(east: Group, south: Group, width: Int, height: Int, count: Int = 0): Int =
        if (east.stuck && south.stuck) count
        else {
            val newEast = move(Direction.EAST, east, south, width, height)
            val newSouth = move(Direction.SOUTH, newEast, south, width, height)

            moveAll(newEast, newSouth, width, height, count + 1)
        }

    private fun move(direction: Direction, east: Group, south: Group, width: Int, height: Int) =
        when (direction) {
            Direction.EAST -> east.positions
            Direction.SOUTH -> south.positions
        }.let { cucumbers ->
            cucumbers.map { cucumber ->
                val newCucumber = when (direction) {
                    Direction.EAST -> cucumber.copy(hor = (cucumber.hor + 1) % width)
                    Direction.SOUTH -> cucumber.copy(ver = (cucumber.ver + 1) % height)
                }

                when {
                    east.positions.contains(newCucumber) -> cucumber
                    south.positions.contains(newCucumber) -> cucumber
                    else -> newCucumber
                }
            }
                .toSet()
                .let { newCucumbers -> Group(newCucumbers, cucumbers == newCucumbers) }
        }

    private enum class Direction { EAST, SOUTH }

    private data class Position(val ver: Int, val hor: Int)

    private data class Group(val positions: Set<Position>, val stuck: Boolean = false)
}
