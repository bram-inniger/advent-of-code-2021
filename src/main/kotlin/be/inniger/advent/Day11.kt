package be.inniger.advent

object Day11 {

    private const val MAX_ENERGY = 9
    private const val MIN_ENERGY = 0
    private const val GRID_DIMENSION = 10

    private val positions = (0 until GRID_DIMENSION)
        .flatMap { ver -> (0 until GRID_DIMENSION).map { hor -> Position(ver, hor) } }

    fun solveFirst(grid: List<String>) =
        countFlashes(StepResult(grid.map { line -> line.map { octopus -> octopus.digitToInt() } }), 100)

    fun solveSecond(grid: List<String>) =
        findSync(StepResult(grid.map { line -> line.map { octopus -> octopus.digitToInt() } }))

    private tailrec fun countFlashes(result: StepResult, nrTurns: Int, nrFlashes: Int = 0): Int =
        if (nrTurns == 0) nrFlashes
        else {
            val nextResult = step(result.grid)
            countFlashes(nextResult, nrTurns - 1, nrFlashes + nextResult.flashed.size)
        }

    private tailrec fun findSync(result: StepResult, step: Int = 0): Int =
        if (result.flashed.size == GRID_DIMENSION * GRID_DIMENSION) step
        else findSync(step(result.grid), step + 1)

    private tailrec fun step(
        grid: List<List<Int>>, flashed: Set<Position> = setOf(), increment: Boolean = true
    ): StepResult =
        when {
            increment -> step(increment(grid), flashed, increment = false)
            shouldFlash(grid, flashed) -> {
                val result = flash(grid, flashed)
                step(result.grid, result.flashed, increment = false)
            }
            else -> StepResult(reset(grid), flashed)
        }

    private fun increment(grid: List<List<Int>>) =
        grid.map { line -> line.map { octopus -> octopus + 1 } }

    private fun shouldFlash(grid: List<List<Int>>, flashed: Set<Position>) =
        positions.any { grid[it.ver][it.hor] > MAX_ENERGY && !flashed.contains(it) }

    private fun flash(grid: List<List<Int>>, flashed: Set<Position>): StepResult {
        val toFlash = positions.first { grid[it.ver][it.hor] > MAX_ENERGY && !flashed.contains(it) }
        val flashedGrid = grid.mapIndexed { ver, line ->
            List(line.size) { hor ->
                grid[ver][hor] + if (areNeighbours(toFlash, ver, hor)) 1 else 0
            }
        }

        return StepResult(flashedGrid, flashed + toFlash)
    }

    private fun reset(grid: List<List<Int>>) =
        grid.map { line -> line.map { octopus -> if (octopus > MAX_ENERGY) MIN_ENERGY else octopus } }

    private fun areNeighbours(p: Position, ver: Int, hor: Int) =
        (p.hor == hor - 1 || p.hor == hor || p.hor == hor + 1) &&
                (p.ver == ver - 1 || p.ver == ver || p.ver == ver + 1) &&
                !(p.hor == hor && p.ver == ver)


    private data class Position(val hor: Int, val ver: Int)

    private data class StepResult(val grid: List<List<Int>>, val flashed: Set<Position> = setOf())
}
