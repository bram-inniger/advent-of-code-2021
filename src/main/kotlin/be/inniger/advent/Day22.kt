package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day22 {

    private const val DIM = 50

    fun solveFirst(steps: List<String>) = countOn(steps, runAll = false)
    fun solveSecond(stepsText: List<String>) = countOn(stepsText, runAll = true)

    private fun countOn(steps: List<String>, runAll: Boolean) =
        steps.map { Step.of(it) }
            .filter {
                runAll ||
                        (it.cube.x.first >= -DIM && it.cube.x.last <= DIM &&
                                it.cube.y.first >= -DIM && it.cube.y.last <= DIM &&
                                it.cube.z.first >= -DIM && it.cube.z.last <= DIM)
            }
            .findOn()
            .sumOf { it.size() }

    private tailrec fun List<Step>.findOn(on: List<Cuboid> = emptyList()): List<Cuboid> =
        if (this.isEmpty()) on
        else {
            val (state, cutter) = this.head()
            val subtracted = on.flatMap { subtract(it, cutter) }
            val newOn = when (state) {
                State.ON -> subtracted + cutter
                State.OFF -> subtracted
            }

            this.tail().findOn(newOn)
        }

    private tailrec fun subtract(cuttee: Cuboid, cutter: Cuboid, on: List<Cuboid> = emptyList()): List<Cuboid> =
        when {
            cuttee.x.first > cutter.x.last || cuttee.x.last < cutter.x.first ||
                    cuttee.y.first > cutter.y.last || cuttee.y.last < cutter.y.first ||
                    cuttee.z.first > cutter.z.last || cuttee.z.last < cutter.z.first -> listOf(cuttee)
            cuttee.x.first < cutter.x.first -> subtract(
                cuttee.copy(x = cutter.x.first..cuttee.x.last),
                cutter,
                on + cuttee.copy(x = cuttee.x.first until cutter.x.first)
            )
            cuttee.x.last > cutter.x.last -> subtract(
                cuttee.copy(x = cuttee.x.first..cutter.x.last),
                cutter,
                on + cuttee.copy(x = cutter.x.last + 1..cuttee.x.last)
            )
            cuttee.y.first < cutter.y.first -> subtract(
                cuttee.copy(y = cutter.y.first..cuttee.y.last),
                cutter,
                on + cuttee.copy(y = cuttee.y.first until cutter.y.first)
            )
            cuttee.y.last > cutter.y.last -> subtract(
                cuttee.copy(y = cuttee.y.first..cutter.y.last),
                cutter,
                on + cuttee.copy(y = cutter.y.last + 1..cuttee.y.last)
            )
            cuttee.z.first < cutter.z.first -> subtract(
                cuttee.copy(z = cutter.z.first..cuttee.z.last),
                cutter,
                on + cuttee.copy(z = cuttee.z.first until cutter.z.first)
            )
            cuttee.z.last > cutter.z.last -> subtract(
                cuttee.copy(z = cuttee.z.first..cutter.z.last),
                cutter,
                on + cuttee.copy(z = cutter.z.last + 1..cuttee.z.last)
            )
            else -> on
        }

    private enum class State { ON, OFF }

    private data class Cuboid(val x: LongRange, val y: LongRange, val z: LongRange) {
        fun size() = (x.last - x.first + 1) * (y.last - y.first + 1) * (z.last - z.first + 1)
    }

    private data class Step(val state: State, val cube: Cuboid) {
        companion object {
            private val regex = """^(on|off) x=(-?\d+)\.\.(-?\d+),y=(-?\d+)\.\.(-?\d+),z=(-?\d+)\.\.(-?\d+)$"""
                .toRegex()

            fun of(step: String): Step {
                val (state, xMin, xMax, yMin, yMax, zMin, zMax) = regex.find(step)!!.destructured
                return Step(
                    when (state) {
                        "on" -> State.ON
                        "off" -> State.OFF
                        else -> error("Invalid state $state")
                    },
                    Cuboid(
                        xMin.toLong()..xMax.toLong(),
                        yMin.toLong()..yMax.toLong(),
                        zMin.toLong()..zMax.toLong()
                    )
                )
            }
        }
    }
}
