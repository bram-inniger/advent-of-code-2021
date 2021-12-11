package be.inniger.advent

object Day11 {

    private const val MAX_ENERGY = 9
    private const val MIN_ENERGY = 0
    private const val GRID_DIMENSION = 10

    private val positions = (0 until GRID_DIMENSION)
        .flatMap { ver -> (0 until GRID_DIMENSION).map { hor -> Position(ver, hor) } }

    fun solveFirst(grid: List<String>) =
        step(grid.map { line -> line.map { octopus -> octopus.digitToInt() } }, 100)

    private tailrec fun step(
        grid: List<List<Int>>,
        steps: Int,
        flashed: Set<Position> = setOf(),
        increment: Boolean = true,
        nrFlashed: Int = 0
    ): Int =
        when {
            steps == 0 -> nrFlashed
            increment -> step(increment(grid), steps, flashed, increment = false, nrFlashed)
            shouldFlash(grid, flashed) -> {
                val result = flash(grid, flashed)
                step(result.grid, steps, result.flashed, increment = false, nrFlashed)
            }
            else -> step(reset(grid), steps - 1, flashed = setOf(), increment = true, nrFlashed + flashed.size)
        }

    private fun increment(grid: List<List<Int>>): List<List<Int>> =
        grid.map { line -> line.map { octopus -> octopus + 1 } }

    private fun shouldFlash(grid: List<List<Int>>, flashed: Set<Position>) =
        positions.any { grid[it.ver][it.hor] > MAX_ENERGY && !flashed.contains(it) }

    private fun flash(grid: List<List<Int>>, flashed: Set<Position>): FlashResult {
        val toFlash = positions.first { grid[it.ver][it.hor] > MAX_ENERGY && !flashed.contains(it) }
        val flashedGrid = grid.mapIndexed { ver, line ->
            List(line.size) { hor ->
                grid[ver][hor] + if (areNeighbours(toFlash, ver, hor)) 1 else 0
            }
        }

        return FlashResult(flashedGrid, flashed + toFlash)
    }

    private fun reset(grid: List<List<Int>>): List<List<Int>> =
        grid.map { line -> line.map { octopus -> if (octopus > MAX_ENERGY) MIN_ENERGY else octopus } }

    private fun areNeighbours(p: Position, ver: Int, hor: Int) =
        (p.hor == hor - 1 || p.hor == hor || p.hor == hor + 1) &&
                (p.ver == ver - 1 || p.ver == ver || p.ver == ver + 1) &&
                !(p.hor == hor && p.ver == ver)


    private data class Position(val hor: Int, val ver: Int)

    private data class FlashResult(val grid: List<List<Int>>, val flashed: Set<Position>)
}
