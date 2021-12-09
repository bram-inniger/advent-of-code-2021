package be.inniger.advent

import be.inniger.advent.util.UnionFind

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

    fun solveSecond(gridText: List<String>): Int {
        val grid = gridText.map { line -> line.map { depth -> depth.digitToInt() } }
        val uf = UnionFind(grid.size * grid.first().size)
        val coord = { ver: Int, hor: Int -> hor + ver * grid.first().size }

        for (ver in grid.indices) {
            for (hor in grid.first().indices) {
                if (grid[ver][hor] != 9) {
                    if (ver < grid.lastIndex && grid[ver + 1][hor] != 9) {
                        uf.union(coord(ver, hor), coord(ver + 1, hor))
                    }
                    if (hor < grid.first().lastIndex && grid[ver][hor + 1] != 9) {
                        uf.union(coord(ver, hor), coord(ver, hor + 1))
                    }
                }
            }
        }

        return uf.getSizes()
            .filter { it != 1 }
            .sorted()
            .reversed()
            .subList(0, 3)
            .reduceRight { a, b -> a * b }
    }

    private fun neighbours(ver: Int, hor: Int, grid: List<List<Int>>) =
        listOfNotNull(
            if (ver > 0) grid[ver - 1][hor] else null,
            if (ver < grid.lastIndex) grid[ver + 1][hor] else null,
            if (hor > 0) grid[ver][hor - 1] else null,
            if (hor < grid.first().lastIndex) grid[ver][hor + 1] else null
        )
}
