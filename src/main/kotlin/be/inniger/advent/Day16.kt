package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day16 {
    private const val HEX = 16
    private const val BIN = 2
    private const val PADDING = 4
    private const val VERSION = 3
    private const val TYPE = 3
    private const val TYPE_ID = 1
    private const val LITERAL_NEXT = 1
    private const val LITERAL = 5
    private const val B_LENGTH = 15
    private const val NR_PACKS = 11

    fun solveFirst(transmission: String) = sumVersions(listOf(parse(toBin(transmission))))

    private fun toBin(hex: String) =
        hex.map { it.digitToInt(HEX).toString(BIN).padStart(PADDING, '0') }
            .joinToString("")

    private fun parse(packet: String): Packet {
        val version = packet.substring(0, VERSION).toInt(BIN)
        val type = if (packet.substring(VERSION, VERSION + TYPE) == "100") PacketType.LITERAL else PacketType.OTHER
        val content = packet.substring(VERSION + TYPE)
        val lengthType = LengthType.of(content.first())

        return when (type) {
            PacketType.LITERAL -> {
                val length = VERSION + TYPE + literal(content).length / (LITERAL - LITERAL_NEXT) * LITERAL
                Packet(version, type, listOf(), length)
            }
            PacketType.OTHER -> when (lengthType) {
                LengthType.BIT_LENGTH -> {
                    val bitLength = content.substring(TYPE_ID, TYPE_ID + B_LENGTH).toInt(BIN)
                    val containedPackets = parseBitLength(
                        content.substring(TYPE_ID + B_LENGTH).substring(0, bitLength), bitLength
                    )
                    val length = VERSION + TYPE + TYPE_ID + B_LENGTH + containedPackets.sumOf { it.length }
                    Packet(version, type, containedPackets, length)
                }
                LengthType.NR_PACKETS -> {
                    val nrPackets = content.substring(TYPE_ID, TYPE_ID + NR_PACKS).toInt(BIN)
                    val containedPackets = parseNrPackets(content.substring(TYPE_ID + NR_PACKS), nrPackets)
                    val length = VERSION + TYPE + TYPE_ID + NR_PACKS + containedPackets.sumOf { it.length }
                    Packet(version, type, containedPackets, length)
                }
            }
        }
    }

    private tailrec fun parseBitLength(
        packets: String,
        bitLength: Int,
        parsedPackets: List<Packet> = listOf()
    ): List<Packet> =
        if (packets.isEmpty()) parsedPackets
        else {
            val packet = parse(packets)
            parseBitLength(packets.substring(packet.length), bitLength - packet.length, parsedPackets + packet)
        }

    private tailrec fun parseNrPackets(
        packets: String,
        nrPackets: Int,
        parsedPackets: List<Packet> = listOf()
    ): List<Packet> =
        if (nrPackets == 0) parsedPackets
        else {
            val packet = parse(packets)
            parseNrPackets(packets.substring(packet.length), nrPackets - 1, parsedPackets + packet)
        }

    private tailrec fun sumVersions(packets: List<Packet>, sum: Int = 0): Int =
        if (packets.isEmpty()) sum
        else {
            val packet = packets.head()
            val newPackets = packets.tail() + packet.packets

            sumVersions(newPackets, sum + packet.version)
        }

    private tailrec fun literal(content: String, value: String = ""): String =
        if (content.first() == '0') (value + content.substring(LITERAL_NEXT, LITERAL))
        else literal(content.substring(LITERAL), value + content.substring(LITERAL_NEXT, LITERAL))

    private enum class PacketType(val typeId: String) {
        LITERAL("100"),
        OTHER("");

        companion object {
            val lut = values().associateBy { it.typeId }
        }
    }

    private enum class LengthType {
        BIT_LENGTH,
        NR_PACKETS;

        companion object {
            fun of(id: Char) = when (id) {
                '0' -> BIT_LENGTH
                '1' -> NR_PACKETS
                else -> error("Invalid length type id $id")
            }
        }
    }

    private data class Packet(val version: Int, val type: PacketType, val packets: List<Packet>, val length: Int)
}
