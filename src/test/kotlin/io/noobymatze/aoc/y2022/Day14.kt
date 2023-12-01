package io.noobymatze.aoc.y2022

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day14 {

    data class Pos(val row: Int, val col: Int) {
        val downLeft: Pos get() = Pos(row + 1, col - 1)
        val downRight: Pos get() = Pos(row + 1, col + 1)
        val down: Pos get() = Pos(row + 1, col)
    }

    @Test
    fun test() {
        val rocks = Aoc.getInput(14)
            .lineSequence()
            .flatMap { line ->
                line.split(" -> ").map {
                    val (col, row) = it.split(",").map(String::toInt)
                    Pos(row, col)
                }.zipWithNext().flatMap { (from, to) ->
                    (minOf(from.row, to.row)..maxOf(from.row, to.row)).map {
                        Pos(it, from.col)
                    } + (minOf(from.col, to.col)..maxOf(from.col, to.col)).map {
                        Pos(from.row, it)
                    }
                }.toSet()
            }.toSet()

        val sands = mutableSetOf<Pos>()
        val start = Pos(0, 500)
        println(printBoard(rocks, sands, start))

        fun Pos.isBlocked(): Boolean =
            this in sands || this in rocks

        val maxRow = rocks.maxBy { it.row }.row
        var sand = start
        while (sand.row < maxRow) {
            sand = when {
                sand.down.isBlocked() && sand.downLeft.isBlocked() && sand.downRight.isBlocked() -> {
                    sands.add(sand)
                    start
                }

                sand.down.isBlocked() && sand.downLeft.isBlocked() ->
                    sand.downRight

                sand.down.isBlocked() ->
                    sand.downLeft

                else ->
                    sand.down
            }
        }

        println(sands.size)
        println(printBoard(rocks, sands, start))
    }

    @Test
    fun test2() {
        val rocks = Aoc.getInput(14)
            .lineSequence()
            .flatMap { line ->
                line.split(" -> ").map {
                    val (col, row) = it.split(",").map(String::toInt)
                    Pos(row, col)
                }.zipWithNext().flatMap { (from, to) ->
                    (minOf(from.row, to.row)..maxOf(from.row, to.row)).map {
                        Pos(it, from.col)
                    } + (minOf(from.col, to.col)..maxOf(from.col, to.col)).map {
                        Pos(from.row, it)
                    }
                }.toSet()
            }.toSet()

        val sands = mutableSetOf<Pos>()
        val start = Pos(0, 500)
        println(printBoard(rocks, sands, start))

        fun Pos.isBlocked(): Boolean =
            this in sands || this in rocks

        val maxRow = rocks.maxBy { it.row }.row + 2
        var sand = start
        while (!start.isBlocked()) {
            sand = when {
                sand.down.row == maxRow || (sand.down.isBlocked() && sand.downLeft.isBlocked() && sand.downRight.isBlocked()) -> {
                    sands.add(sand)
                    start
                }

                sand.down.isBlocked() && sand.downLeft.isBlocked() ->
                    sand.downRight

                sand.down.isBlocked() ->
                    sand.downLeft

                else ->
                    sand.down
            }
        }

        println(sands.size)
        println(printBoard(rocks, sands, start))
    }

    private fun printBoard(
        rocks: Set<Pos>,
        sands: Set<Pos>,
        start: Pos,
    ): String {
        val maxRow = rocks.maxBy { it.row }.row
        val minCol = rocks.minBy { it.col }.col
        val maxCol = rocks.maxBy { it.col }.col
        return (0..maxRow).joinToString("\n") { row ->
            (minCol..maxCol).joinToString("") { col ->
                val pos = Pos(row, col)
                when {
                    pos in rocks -> "#"
                    pos == start -> "+"
                    pos in sands -> "o"
                    else -> "."
                }
            }
        }
    }

}