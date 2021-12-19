package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day16Test {

    private val input = readInputFile("16").first()
    private val sampleTransmission0 = "D2FE28"
    private val sampleTransmission1 = "8A004A801A8002F478"
    private val sampleTransmission2 = "620080001611562C8802118E34"
    private val sampleTransmission3 = "C0015000016115A2E0802F182340"
    private val sampleTransmission4 = "A0016C880162017C3686B18A3D4780"

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(6, Day16.solveFirst(sampleTransmission0))
        assertEquals(16, Day16.solveFirst(sampleTransmission1))
        assertEquals(12, Day16.solveFirst(sampleTransmission2))
        assertEquals(23, Day16.solveFirst(sampleTransmission3))
        assertEquals(31, Day16.solveFirst(sampleTransmission4))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(960, Day16.solveFirst(input))
    }
}
