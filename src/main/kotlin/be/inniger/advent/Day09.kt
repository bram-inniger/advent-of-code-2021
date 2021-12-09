package be.inniger.advent

object Day09 {

    fun solveFirst(gridText: List<String>): Int {
        val grid = gridText.map { line -> line.map { depth -> depth.digitToInt() } }

        return grid.indices.flatMap { hor ->
            grid.first().indices
                .filter { ver -> neighbours(hor, ver, grid).all { neighbour -> grid[hor][ver] < neighbour } }
                .map { ver -> grid[hor][ver] }
                .map { depth -> depth + 1 }
        }.sum()
    }

    private fun neighbours(ver: Int, hor: Int, grid: List<List<Int>>) =
        listOfNotNull(
            if (ver > 0) grid[ver - 1][hor] else null,
            if (ver < grid.lastIndex) grid[ver + 1][hor] else null,
            if (hor > 0) grid[ver][hor - 1] else null,
            if (hor < grid.first().lastIndex) grid[ver][hor + 1] else null
        )
}
