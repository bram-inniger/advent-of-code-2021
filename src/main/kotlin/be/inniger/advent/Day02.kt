package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day02 {

    fun solveFirst(commands: List<String>): Int {
        val endPosition = move(commands.map { Command.of(it) })
        return endPosition.depth * endPosition.horizontal
    }

    private tailrec fun move(commands: List<Command>, position: Position = Position(0, 0)): Position {
        return if (commands.isEmpty()) position
        else {
            val command = commands.head()
            val newPosition = when (command.direction) {
                Direction.FORWARD -> position.copy(horizontal = position.horizontal + command.distance)
                Direction.UP -> position.copy(depth = position.depth - command.distance)
                Direction.DOWN -> position.copy(depth = position.depth + command.distance)
            }

            move(commands.tail(), newPosition)
        }
    }

    private data class Command(val direction: Direction, val distance: Int) {

        companion object {
            private val regex = """^(\w+) (\d+)$""".toRegex()

            fun of(command: String): Command {
                val (direction, distance) = regex.find(command)!!.destructured
                return Command(
                    Direction.valueOf(direction.uppercase()),
                    distance.toInt(),
                )
            }
        }
    }

    private enum class Direction {
        FORWARD, UP, DOWN
    }

    private data class Position(val horizontal: Int, val depth: Int)
}
