package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day08Test {

    private val input = readInputFile("08")
    private val sampleEntriesSingle = listOf(
        "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf",
    )
    private val sampleEntriesMultiple = listOf(
        "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe",
        "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc",
        "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg",
        "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb",
        "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea",
        "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb",
        "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe",
        "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef",
        "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb",
        "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce",
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(0, Day08.solveFirst(sampleEntriesSingle))
        assertEquals(26, Day08.solveFirst(sampleEntriesMultiple))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(412, Day08.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(5_353, Day08.solveSecond(sampleEntriesSingle))
        assertEquals(61_229, Day08.solveSecond(sampleEntriesMultiple))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(978_171, Day08.solveSecond(input))
    }
}
