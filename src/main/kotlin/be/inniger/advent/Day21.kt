package be.inniger.advent

import kotlin.math.max
import kotlin.math.min

object Day21 {

    private const val MAX_SCORE = 1000
    private const val MAX_SCORE_DIRAC = 21
    private const val NR_ROLLS = 3
    private const val D_100 = 100
    private const val NR_SPACES = 10

    private val playerRegex = """^Player [12] starting position: (\d|10)$""".toRegex()
    private val rolls = (1..3).flatMap { first ->
        (1..3).flatMap { second ->
            (1..3).map { third -> first + second + third }
        }
    }
        .groupingBy { it }
        .eachCount()
        .map { RollDirac(it.key, it.value) }

    fun solveFirst(positions: List<String>) =
        playSimple(GameSimple(PlayerId.ONE, Player(starts(positions[0]), 0), Player(starts(positions[1]), 0)))
            .let { min(it.playerOne.score, it.playerTwo.score) * it.die.nrRolls }

    fun solveSecond(positions: List<String>) =
        playDirac(GameDirac(PlayerId.ONE, Player(starts(positions[0]), 0), Player(starts(positions[1]), 0)))
            .let { max(it.playerOne, it.playerTwo) }

    private fun starts(player: String) =
        playerRegex.find(player)!!.groupValues[1].toInt()

    private tailrec fun playSimple(game: GameSimple): GameSimple =
        if (game.playerOne.score >= MAX_SCORE || game.playerTwo.score >= MAX_SCORE) game
        else {
            val roll = roll(RollResult(game.die))
            when (game.playerId) {
                PlayerId.ONE -> {
                    val newPosition = (game.playerOne.position - 1 + roll.nrSpaces) % NR_SPACES + 1
                    val newPlayer = Player(newPosition, game.playerOne.score + newPosition)
                    playSimple(GameSimple(PlayerId.TWO, newPlayer, game.playerTwo, roll.die))
                }
                PlayerId.TWO -> {
                    val newPosition = (game.playerTwo.position - 1 + roll.nrSpaces) % NR_SPACES + 1
                    val newPlayer = Player(newPosition, game.playerTwo.score + newPosition)
                    playSimple(GameSimple(PlayerId.ONE, game.playerOne, newPlayer, roll.die))
                }
            }
        }

    private fun playDirac(game: GameDirac, wins: MutableMap<GameDirac, Score> = mutableMapOf()): Score =
        when {
            wins.containsKey(game) -> wins[game]!!
            game.playerOne.score >= MAX_SCORE_DIRAC -> wins.computeIfAbsent(game) { Score(1, 0) }
            game.playerTwo.score >= MAX_SCORE_DIRAC -> wins.computeIfAbsent(game) { Score(0, 1) }
            else -> {
                val score = when (game.playerId) {
                    PlayerId.ONE -> {
                        rolls.map { roll ->
                            val newPosition = (game.playerOne.position - 1 + roll.nrSpaces) % NR_SPACES + 1
                            val newPlayer = Player(newPosition, game.playerOne.score + newPosition)
                            val localScore = playDirac(GameDirac(PlayerId.TWO, newPlayer, game.playerTwo), wins)
                            Score(localScore.playerOne * roll.frequency, localScore.playerTwo * roll.frequency)
                        }.reduce { acc, score ->
                            Score(
                                acc.playerOne + score.playerOne,
                                acc.playerTwo + score.playerTwo
                            )
                        }
                    }
                    PlayerId.TWO -> {
                        rolls.map { roll ->
                            val newPosition = (game.playerTwo.position - 1 + roll.nrSpaces) % NR_SPACES + 1
                            val newPlayer = Player(newPosition, game.playerTwo.score + newPosition)
                            val localScore = playDirac(GameDirac(PlayerId.ONE, game.playerOne, newPlayer), wins)
                            Score(localScore.playerOne * roll.frequency, localScore.playerTwo * roll.frequency)
                        }.reduce { acc, score ->
                            Score(
                                acc.playerOne + score.playerOne,
                                acc.playerTwo + score.playerTwo
                            )
                        }
                    }
                }
                wins[game] = score
                score
            }
        }

    private tailrec fun roll(roll: RollResult, nrRolls: Int = NR_ROLLS): RollResult =
        if (nrRolls == 0) roll
        else {
            val newDie = Die(roll.die.value % D_100 + 1, roll.die.nrRolls + 1)
            roll(RollResult(newDie, roll.nrSpaces + newDie.value), nrRolls - 1)
        }

    private enum class PlayerId { ONE, TWO }

    private data class Player(val position: Int, val score: Int)

    private data class Die(val value: Int = 0, val nrRolls: Int = 0)

    private data class GameSimple(
        val playerId: PlayerId,
        val playerOne: Player,
        val playerTwo: Player,
        val die: Die = Die()
    )

    private data class GameDirac(val playerId: PlayerId, val playerOne: Player, val playerTwo: Player)

    private data class RollResult(val die: Die, val nrSpaces: Int = 0)

    private data class RollDirac(val nrSpaces: Int, val frequency: Int)

    private data class Score(val playerOne: Long, val playerTwo: Long)
}
