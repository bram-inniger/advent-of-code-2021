package be.inniger.advent

object Day20 {

    private const val PADDING = 10

    fun solveFirst(scanner: String): Int {
        val (algorithmText, imageText) = scanner.split("\n\n")
        val splitImageText = imageText.split("\n")

        val algorithm = parseAlgorithm(algorithmText)
        val image = parseImage(splitImageText)

        return enhance(enhance(image, algorithm), algorithm)
            .count { it.hor in -PADDING / 2..splitImageText.size + PADDING / 2 && it.ver in -PADDING / 2..105 }
    }

    private fun parseAlgorithm(algorithm: String) =
        algorithm.indices.filter { algorithm[it] == '#' }.toSet()

    private fun parseImage(image: List<String>) =
        image.flatMapIndexed { ver: Int, line: String ->
            line.mapIndexed { hor, pixel -> if (pixel == '#') Position(ver, hor) else null }
        }
            .filterNotNull()
            .toSet()

    private fun enhance(image: Set<Position>, algorithm: Set<Int>): Set<Position> {
        val minVer = image.minOf { it.hor } - PADDING
        val maxVer = image.maxOf { it.hor } + PADDING
        val minHor = image.minOf { it.hor } - PADDING
        val maxHor = image.maxOf { it.hor } + PADDING

        return (minVer..maxVer).flatMap { ver ->
            (minHor..maxHor).map { hor -> Position(ver, hor) }
        }
            .filter { shouldLightUp(it, image, algorithm) }
            .toSet()
    }

    private fun shouldLightUp(position: Position, image: Set<Position>, algorithm: Set<Int>) =
        (position.ver - 1..position.ver + 1).flatMap { ver ->
            (position.hor - 1..position.hor + 1).map { hor -> Position(ver, hor) }
        }
            .map { if (image.contains(it)) '1' else '0' }
            .joinToString("")
            .toInt(2)
            .let { algorithm.contains(it) }

    private data class Position(val ver: Int, val hor: Int)
}
