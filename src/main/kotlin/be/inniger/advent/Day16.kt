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
    fun solveSecond(transmission: String) = parse(toBin(transmission)).value

    private fun toBin(hex: String) =
        hex.map { it.digitToInt(HEX).toString(BIN).padStart(PADDING, '0') }
            .joinToString("")

    private fun parse(packet: String): Packet {
        val version = packet.substring(0, VERSION).toInt(BIN)
        val type = PacketType.lut[packet.substring(VERSION, VERSION + TYPE)]!!
        val content = packet.substring(VERSION + TYPE)
        val lengthType = LengthType.of(content.first())

        return if (type == PacketType.LITERAL) {
            val value = literal(content)
            val length = VERSION + TYPE + value.length / (LITERAL - LITERAL_NEXT) * LITERAL
            Packet(version, type, listOf(), length, value.toLong(BIN))
        } else when (lengthType) {
            LengthType.BIT_LENGTH -> {
                val bitLength = content.substring(TYPE_ID, TYPE_ID + B_LENGTH).toInt(BIN)
                val containedPackets = parseBitLength(
                    content.substring(TYPE_ID + B_LENGTH).substring(0, bitLength), bitLength
                )
                val length = VERSION + TYPE + TYPE_ID + B_LENGTH + containedPackets.sumOf { it.length }
                Packet(version, type, containedPackets, length, calculateValue(type, containedPackets))
            }
            LengthType.NR_PACKETS -> {
                val nrPackets = content.substring(TYPE_ID, TYPE_ID + NR_PACKS).toInt(BIN)
                val containedPackets = parseNrPackets(content.substring(TYPE_ID + NR_PACKS), nrPackets)
                val length = VERSION + TYPE + TYPE_ID + NR_PACKS + containedPackets.sumOf { it.length }
                Packet(version, type, containedPackets, length, calculateValue(type, containedPackets))
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

    private fun calculateValue(type: PacketType, packets: List<Packet>) =
        when (type) {
            PacketType.SUM -> packets.sumOf { it.value }
            PacketType.PRODUCT -> packets.map { it.value }.reduce { acc, packet -> acc * packet }
            PacketType.MINIMUM -> packets.minOf { it.value }
            PacketType.MAXIMUM -> packets.maxOf { it.value }
            PacketType.LITERAL -> error("Illegal operation $type on packets $packets")
            PacketType.GREATER_THAN -> if (packets[0].value > packets[1].value) 1 else 0
            PacketType.LESS_THAN -> if (packets[0].value < packets[1].value) 1 else 0
            PacketType.EQUAL_TO -> if (packets[0].value == packets[1].value) 1 else 0
        }

    private enum class PacketType(val typeId: String) {
        SUM("000"),
        PRODUCT("001"),
        MINIMUM("010"),
        MAXIMUM("011"),
        LITERAL("100"),
        GREATER_THAN("101"),
        LESS_THAN("110"),
        EQUAL_TO("111");

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

    private data class Packet(
        val version: Int,
        val type: PacketType,
        val packets: List<Packet>,
        val length: Int,
        val value: Long
    )
}
