package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day02 {

    fun solveFirst(commands: List<String>): Int {
        val endPosition = move(commands.map { Command.of(it) }, this::calculateSimplePosition)
        return endPosition.depth * endPosition.horizontal
    }

    fun solveSecond(commands: List<String>): Int {
        val endPosition = move(commands.map { Command.of(it) }, this::calculateComplicatedPosition)
        return endPosition.depth * endPosition.horizontal
    }

    private tailrec fun move(
        commands: List<Command>,
        positionCalculator: (Command, Position) -> Position,
        position: Position = Position()
    ): Position =
        if (commands.isEmpty()) position
        else move(
            commands.tail(),
            positionCalculator,
            positionCalculator(commands.head(), position)
        )

    private fun calculateSimplePosition(command: Command, position: Position) =
        when (command.direction) {
            Direction.FORWARD -> position.copy(horizontal = position.horizontal + command.distance)
            Direction.UP -> position.copy(depth = position.depth - command.distance)
            Direction.DOWN -> position.copy(depth = position.depth + command.distance)
        }

    private fun calculateComplicatedPosition(command: Command, position: Position) =
        when (command.direction) {
            Direction.FORWARD -> Position(
                position.horizontal + command.distance,
                position.depth + command.distance * position.aim,
                position.aim
            )
            Direction.UP -> position.copy(aim = position.aim - command.distance)
            Direction.DOWN -> position.copy(aim = position.aim + command.distance)
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

    private data class Position(
        val horizontal: Int = 0, val depth: Int = 0, val aim: Int = 0
    )
}
