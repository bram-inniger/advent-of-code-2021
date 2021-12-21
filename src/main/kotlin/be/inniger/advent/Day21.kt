package be.inniger.advent

import kotlin.math.min

object Day21 {

    private const val MAX_SCORE = 1000
    private const val NR_ROLLS = 3
    private const val D_100 = 100
    private const val NR_SPACES = 10

    private val playerRegex = """^Player [12] starting position: (\d+)$""".toRegex()

    fun solveFirst(positions: List<String>) =
        play(
            Game(
                PlayerId.ONE,
                Player(starts(positions[0]), 0),
                Player(starts(positions[1]), 0),
                Die.DeterministicDie()
            )
        )
            .let { min(it.playerOne.score, it.playerTwo.score) * it.die.nrRolls }

    private fun starts(player: String) =
        playerRegex.find(player)!!.groupValues[1].toInt()

    private tailrec fun play(game: Game): Game =
        if (game.playerOne.score >= MAX_SCORE || game.playerTwo.score >= MAX_SCORE) game
        else {
            val roll = roll(Roll(game.die))
            when (game.playerId) {
                PlayerId.ONE -> {
                    val newPosition = (game.playerOne.position - 1 + roll.nrSpaces) % NR_SPACES + 1
                    val newPlayer = Player(newPosition, game.playerOne.score + newPosition)
                    play(Game(PlayerId.TWO, newPlayer, game.playerTwo, roll.die))
                }
                PlayerId.TWO -> {
                    val newPosition = (game.playerTwo.position - 1 + roll.nrSpaces) % NR_SPACES + 1
                    val newPlayer = Player(newPosition, game.playerTwo.score + newPosition)
                    play(Game(PlayerId.ONE, game.playerOne, newPlayer, roll.die))
                }
            }
        }

    private tailrec fun roll(roll: Roll, nrRolls: Int = NR_ROLLS): Roll =
        if (nrRolls == 0) roll
        else {
            val newDie = when (roll.die) {
                is Die.DeterministicDie -> Die.DeterministicDie(roll.die.value % D_100 + 1, roll.die.nrRolls + 1)
            }

            roll(Roll(newDie, roll.nrSpaces + newDie.value), nrRolls - 1)
        }

    private sealed class Die {
        abstract val nrRolls: Int

        data class DeterministicDie(val value: Int = 0, override val nrRolls: Int = 0) : Die()
    }

    private enum class PlayerId { ONE, TWO }

    private data class Player(val position: Int, val score: Int)

    private data class Game(val playerId: PlayerId, val playerOne: Player, val playerTwo: Player, val die: Die)

    private data class Roll(val die: Die, val nrSpaces: Int = 0)
}
