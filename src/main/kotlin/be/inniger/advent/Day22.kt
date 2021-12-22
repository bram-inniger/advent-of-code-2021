package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day22 {

    private const val DIM = 50

    fun solveFirst(stepsText: List<String>) =
        stepsText.map { Step.of(it) }
            .filter { it.x.first >= -DIM && it.x.last <= DIM }
            .filter { it.y.first >= -DIM && it.y.last <= DIM }
            .filter { it.z.first >= -DIM && it.z.last <= DIM }
            .let {
                (-DIM..DIM).sumOf { x ->
                    (-DIM..DIM).sumOf { y ->
                        (-DIM..DIM).count { z ->
                            isOn(x, y, z, it)
                        }
                    }
                }
            }

    private tailrec fun isOn(x: Int, y: Int, z: Int, steps: List<Step>, on: Boolean = false): Boolean =
        if (steps.isEmpty()) on
        else {
            val step = steps.head()
            val contains = contains(x, y, z, step)
            val newOn = when {
                contains && step.state == State.ON -> true
                contains && step.state == State.OFF -> false
                else -> on
            }

            isOn(x, y, z, steps.tail(), newOn)
        }

    private fun contains(x: Int, y: Int, z: Int, step: Step) =
        step.x.contains(x) && step.y.contains(y) && step.z.contains(z)

    private enum class State { ON, OFF }

    private data class Step(val state: State, val x: IntRange, val y: IntRange, val z: IntRange) {
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
                    xMin.toInt()..xMax.toInt(),
                    yMin.toInt()..yMax.toInt(),
                    zMin.toInt()..zMax.toInt()
                )
            }
        }
    }
}
