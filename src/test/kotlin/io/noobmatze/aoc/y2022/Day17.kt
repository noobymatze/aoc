package io.noobmatze.aoc.y2022

import kotlin.math.abs
import kotlin.test.Test

class Day17 {

    data class Pos(val row: Int, val col: Int) {
        val up get() = Pos(row - 1, col)
        val down get() = Pos(row + 1, col)

        // I know, this is a bit dirty
        operator fun plus(value: Int): Pos =
            Pos(row, col + value)
        operator fun minus(value: Int): Pos =
            Pos(row, col - value)
    }

    @Test
    fun test1() {
        val example = """
            |>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
        """.trimMargin()
        val moves = Aoc.getInput(17)
            .asSequence().joinToString("v") + "v"
        var blocked = (0..6).map { Pos(1, it) }.toSet()
        var rockCounter = 0L
        var instr = 0
        var rock = rock(rockCounter, 1)
        while (rockCounter < 2023) {
            val move = moves[instr++ % moves.length]
            val newRock = rock.move(move, blocked)
            rock = if (newRock == null) {
                //printBoard(rock, blocked)
                blocked = calcBlocked(blocked + rock)
                rock(++rockCounter, blocked.minOf { it.row })
            } else {
                newRock
            }
        }

        println(abs(blocked.minOf { it.row }) - 1)
    }

    @Test
    fun test2() {
        val example = """
            |>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
        """.trimMargin()
        val moves = example // Aoc.getInput(17)
            .asSequence().joinToString("v") + "v"
        var blocked = (0..6).map { Pos(1, it) }.toSet()
        var rockCounter = 0L
        var instr = 0
        var rock = rock(rockCounter, 1)
        while (rockCounter < 1000000000000) {
            val move = moves[instr++ % moves.length]
            val newRock = rock.move(move, blocked)
            rock = if (newRock == null) {
                //printBoard(rock, blocked)
                blocked = calcBlocked(blocked + rock)
                rock(++rockCounter, blocked.minOf { it.row })
            } else {
                newRock
            }

            if (rockCounter % 1000 == 0L) {
                println("$rockCounter: ${blocked.size}")
            }
        }

        println(abs(blocked.minOf { it.row }) - 1)
    }

    private fun calcBlocked(blocked: Set<Pos>): Set<Pos> {
        val result = mutableSetOf<Pos>()
        val mins = (0..6).map { col -> blocked.filter { it.col == col }.minOf { it.row } }
        for (pos in blocked) {
            val onTop = pos.row > mins[pos.col]
            val left = pos - 1 in blocked || pos.col - 1 < 0
            val right = pos + 1 in blocked || pos.col + 1 > 6
            val prune = pos.up in blocked && pos - 1 in blocked && pos + 1 in blocked

            if (!prune) {
                result.add(pos)
            }
        }

        return result
    }

    private fun printBoard(rock: Set<Pos>, blocked: Set<Pos>) {
        println("==")
        val minRow = minOf(blocked.minOf { it.row }, minOf(rock.minOf { it.row }))
        for (row in (minRow..0)) {
            println(row.toString().padStart(3, ' ') + (0..6).joinToString("") {
                when (Pos(row, it)) {
                    in blocked -> "#"
                    in rock -> "@"
                    else -> "."
                }
            })
        }
    }

    fun Set<Pos>.move(value: Char, blocked: Set<Pos>): Set<Pos>? = when (value) {
        '>' -> {
            val next = map { it + 1 }.toSet()
            if ((next intersect blocked).isNotEmpty() || next.any { it.col > 6 } )
                this
            else
                next
        }
        '<' -> {
            val next = map { it - 1 }.toSet()
            if ((next intersect blocked).isNotEmpty() || next.any { it.col < 0 })
                this
            else
                next
        }
        'v' -> {
            val next = map { it.down }.toSet()
            if ((next intersect blocked).isNotEmpty())
                null
            else
                next
        }
        else -> throw IllegalArgumentException("Unknown jet push $value")
    }

    tailrec fun rock(value: Long, bottomRow: Int): Set<Pos> {
        val pos = Pos(bottomRow - 4, 2)
        return when (value) {
            0L -> setOf(pos, pos + 1, pos + 2, pos + 3)
            1L -> setOf(pos.up, pos + 1, (pos + 1).up, (pos + 2).up, (pos + 1).up.up)
            2L -> setOf(pos, pos + 1, pos + 2, (pos + 2).up, (pos + 2).up.up)
            3L -> setOf(pos, pos.up, pos.up.up, pos.up.up.up)
            4L -> setOf(pos, pos.up, pos + 1, (pos + 1).up)
            else -> rock(value % 5, bottomRow)
        }
    }

}