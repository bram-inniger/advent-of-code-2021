package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day24 {

    /*
     * Through decompilation of the MONAD program the digit rules below are found,
     * indexed by [index], with index ranging from 01 to 14.
     *
     * [05] = [04] - 5
     * [08] = [07] - 4
     * [10] = [09] + 2
     * [11] = [06] + 8
     * [12] = [03] + 5
     * [13] = [02] - 2
     * [14] = [01] + 6
     *
     * To get the maximum number, start from [01], solve the equations, picking the maximum allowed values,
     * For [01] this would be "3", making [14] equal to "9".
     *
     * To get the minimum number, start from [01], solve the equations, picking the minimum allowed values,
     * For [01] this would be "1", making [14] equal to "7".
     */
    private val maxModelNumber = listOf(3, 9, 4, 9, 4, 1, 9, 5, 7, 9, 9, 9, 7, 9)
    private val minModelNumber = listOf(1, 3, 1, 6, 1, 1, 5, 1, 1, 3, 9, 6, 1, 7)

    fun solveFirst(instructions: List<String>) = validate(maxModelNumber, instructions).join()
    fun solveSecond(instructions: List<String>) = validate(minModelNumber, instructions).join()

    private fun validate(modelNumber: List<Int>, instructions: List<String>) =
        if (monadDecompiled(modelNumber) && monad(modelNumber, instructions.map { Instruction.of(it) })) modelNumber
        else error("Invalid model number: $modelNumber")

    private fun <T> List<T>.join() = this.joinToString("") { it.toString() }

    // The input equals 7 + 7 equal groups that either push or pop the digit to the stack.
    // Decompiling the input's groups creates this equivalent Kotlin program.
    // This program then translates into the digit rules above, allowing to solve Day24 on paper.
    private fun monadDecompiled(modelNumber: List<Int>): Boolean {
        fun ArrayDeque<Int>.peek() = this.lastOrNull() ?: 0
        fun <T> ArrayDeque<T>.push(element: T) = this.addLast(element)
        fun <T> ArrayDeque<T>.pop() = this.removeLast()

        var i = 0

        val z = ArrayDeque<Int>()

        var w = modelNumber[i++]
        if (w != z.peek() + 13) z.push(w + 6)

        w = modelNumber[i++]
        if (w != z.peek() + 15) z.push(w + 7)

        w = modelNumber[i++]
        if (w != z.peek() + 15) z.push(w + 10)

        w = modelNumber[i++]
        if (w != z.peek() + 11) z.push(w + 2)

        w = modelNumber[i++]
        if (w != z.pop() - 7) z.push(w + 16)

        w = modelNumber[i++]
        if (w != z.peek() + 10) z.push(w + 8)

        w = modelNumber[i++]
        if (w != z.peek() + 10) z.push(w + 1)

        w = modelNumber[i++]
        if (w != z.pop() - 5) z.push(w + 10)

        w = modelNumber[i++]
        if (w != z.peek() + 15) z.push(w + 5)

        w = modelNumber[i++]
        if (w != z.pop() - 3) z.push(w + 3)

        w = modelNumber[i++]
        if (w != z.pop() - 0) z.push(w + 5)

        w = modelNumber[i++]
        if (w != z.pop() - 5) z.push(w + 11)

        w = modelNumber[i++]
        if (w != z.pop() - 9) z.push(w + 12)

        w = modelNumber[i]
        if (w != z.pop() - 0) z.push(w + 10)

        return z.isEmpty()
    }

    private tailrec fun monad(
        modelNumber: List<Int>,
        instructions: List<Instruction>,
        memory: Map<Register, Int> = mapOf(Register.W to 0, Register.X to 0, Register.Y to 0, Register.Z to 0)
    ): Boolean =
        when {
            instructions.isEmpty() -> memory[Register.Z] == 0
            instructions.head().opcode == Opcode.INP -> monad(
                modelNumber.tail(),
                instructions.tail(),
                memory + (instructions.head().destination to modelNumber.head())
            )
            else -> {
                val instruction = instructions.head()
                val argumentValue = instruction.argument.value(memory)
                val destination = instruction.destination

                monad(
                    modelNumber,
                    instructions.tail(),
                    memory + (destination to instruction.opcode.operation(memory[destination]!!, argumentValue))
                )
            }
        }

    private enum class Opcode(val operation: (Int, Int) -> Int) {
        INP({ _, _ -> error("Cannot call the INP opcode this way") }),
        ADD({ a, b -> a + b }),
        MUL({ a, b -> a * b }),
        DIV({ a, b -> a / b }),
        MOD({ a, b -> a % b }),
        EQL({ a, b -> if (a == b) 1 else 0 })
    }

    private enum class Register { W, X, Y, Z }

    private data class Instruction(val opcode: Opcode, val destination: Register, val argument: Argument) {
        companion object {
            val regex = """^(\w{3}) ([wxyz]) ?([wxyz]|-?\d+)?$""".toRegex()
            val registers = setOf("w", "x", "y", "z")

            fun of(instruction: String): Instruction {
                val (opcode, destination, arg) = regex.find(instruction)!!.destructured
                return Instruction(
                    Opcode.valueOf(opcode.uppercase()),
                    Register.valueOf(destination.uppercase()),
                    when {
                        arg.isEmpty() -> Argument.None
                        registers.contains(arg) -> Argument.Relative(Register.valueOf(arg.uppercase()))
                        else -> Argument.Absolute(arg.toInt())
                    }
                )
            }
        }
    }

    private sealed class Argument {
        data class Relative(val register: Register) : Argument()
        data class Absolute(val value: Int) : Argument()
        object None : Argument() {
            override fun toString() = "NONE"
        }

        fun value(memory: Map<Register, Int>) =
            when (this) {
                is Relative -> memory[this.register]!!
                is Absolute -> this.value
                is None -> error("Cannot call `value()` on a NONE argument")
            }
    }
}
