package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day16Test {

    private val input = readInputFile("16").first()

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(6, Day16.solveFirst("D2FE28"))
        assertEquals(16, Day16.solveFirst("8A004A801A8002F478"))
        assertEquals(12, Day16.solveFirst("620080001611562C8802118E34"))
        assertEquals(23, Day16.solveFirst("C0015000016115A2E0802F182340"))
        assertEquals(31, Day16.solveFirst("A0016C880162017C3686B18A3D4780"))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(960, Day16.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(3L, Day16.solveSecond("C200B40A82"))
        assertEquals(54L, Day16.solveSecond("04005AC33890"))
        assertEquals(7L, Day16.solveSecond("880086C3E88112"))
        assertEquals(9L, Day16.solveSecond("CE00C43D881120"))
        assertEquals(1L, Day16.solveSecond("D8005AC2A8F0"))
        assertEquals(0L, Day16.solveSecond("F600BC2D8F"))
        assertEquals(0L, Day16.solveSecond("9C005AC2F8F0"))
        assertEquals(1L, Day16.solveSecond("9C0141080250320F1802104A08"))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(12_301_926_782_560L, Day16.solveSecond(input))
    }
}
