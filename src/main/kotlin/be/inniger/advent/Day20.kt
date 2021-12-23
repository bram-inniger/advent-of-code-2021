package be.inniger.advent

object Day20 {

    private const val PADDING = 2

    fun solveFirst(scanner: String) = solve(scanner, 2)

    fun solveSecond(scanner: String) = solve(scanner, 50)

    private fun solve(scanner: String, count: Int): Int {
        val (algorithmText, imageText) = scanner.split("\n\n")
        val splitImageText = imageText.split("\n")

        val algorithm = parseAlgorithm(algorithmText)
        val image = parseImage(splitImageText)

        return enhanceLoop(image, algorithm, count).count()
    }

    private fun parseAlgorithm(algorithm: String) =
        algorithm.indices.filter { algorithm[it] == '#' }.toSet()

    private fun parseImage(image: List<String>) =
        image.flatMapIndexed { ver: Int, line: String ->
            line.mapIndexed { hor, pixel -> if (pixel == '#') Position(ver, hor) else null }
        }
            .filterNotNull()
            .toSet()

    private tailrec fun enhanceLoop(image: Set<Position>, algorithm: Set<Int>, nrTimes: Int): Set<Position> =
        if (nrTimes == 0) image
        else {
            val newImage = enhance(enhance(image, algorithm, blink = false), algorithm, blink = algorithm.contains(0))
            enhanceLoop(newImage, algorithm, nrTimes - 2)
        }

    private fun enhance(image: Set<Position>, algorithm: Set<Int>, blink: Boolean): Set<Position> {
        val minVer = image.minOf { it.ver }
        val maxVer = image.maxOf { it.ver }
        val minHor = image.minOf { it.hor }
        val maxHor = image.maxOf { it.hor }

        return (minVer - PADDING..maxVer + PADDING).flatMap { ver ->
            (minHor - PADDING..maxHor + PADDING).map { hor -> Position(ver, hor) }
        }
            .filter { lightsUp(it, image, blink, minVer, maxVer, minHor, maxHor, algorithm) }
            .toSet()
    }

    private fun lightsUp(
        it: Position,
        image: Set<Position>,
        blink: Boolean,
        minVer: Int,
        maxVer: Int,
        minHor: Int,
        maxHor: Int,
        algorithm: Set<Int>
    ) =
        (it.ver - 1..it.ver + 1).flatMap { ver ->
            (it.hor - 1..it.hor + 1).map { hor -> Position(ver, hor) }
        }
            .map { position ->
                if (image.contains(position) || (blink && isOutside(position, minVer, maxVer, minHor, maxHor)))
                    '1'
                else
                    '0'
            }
            .joinToString("")
            .toInt(2)
            .let { algoPos -> algorithm.contains(algoPos) }

    private fun isOutside(position: Position, minVer: Int, maxVer: Int, minHor: Int, maxHor: Int) =
        position.ver < minVer || position.ver > maxVer || position.hor < minHor || position.hor > maxHor

    private data class Position(val ver: Int, val hor: Int)
}
