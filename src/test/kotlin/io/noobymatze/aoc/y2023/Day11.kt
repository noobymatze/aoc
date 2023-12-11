package io.noobymatze.aoc.y2023

import io.noobymatze.aoc.Aoc
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.test.Test

class Day11 {

    data class Pos(val row: Int, val col: Int)

    @Test
    fun test() {
        val input = Aoc.getInput(11)
            .lines()
            .expandRows()
            .expandCols()

        fun dist(a: Pos, b: Pos): Int =
            abs(a.row - b.row) + abs(a.col - b.col)

        val galaxies = input.flatMapIndexed { row, line ->
            line.mapIndexedNotNull { col, c -> if (c == '#') Pos(row, col) else null }
        }

        val galaxyPairs = galaxies.allPairs().toSet()

        println(galaxyPairs.sumOf { dist(it.first, it.second) })
    }

    @Test
    fun test2() {
        val input = Aoc.getInput(11)
            .lines()

        val rowsToExpand = input.mapIndexedNotNull { i, s ->
            if (s.all { it == '.' }) i else null
        }

        val colsToExpand = input[0].mapIndexedNotNull { col, _ ->
            if (input.all { it[col] == '.' }) col else null
        }

        fun dist(a: Pos, b: Pos): Long {
            val c = colsToExpand.count { min(a.col, b.col) < it && it < max(a.col, b.col) }
            val r = rowsToExpand.count { min(a.row, b.row) < it && it < max(a.row, b.row) }

            val rowDiff = abs(a.row - b.row) + ((r * 1000000) - r)
            val colDiff = abs(a.col - b.col) + ((c * 1000000) - c)

            return (rowDiff + colDiff).toLong()
        }

        val galaxies = input.flatMapIndexed { row, line ->
            line.mapIndexedNotNull { col, c -> if (c == '#') Pos(row, col) else null }
        }.allPairs().toSet()

        println(galaxies.sumOf { dist(it.first, it.second) })
    }

    private fun List<Pos>.allPairs(): List<Pair<Pos, Pos>> {
        val result = mutableListOf<Pair<Pos, Pos>>()
        for ((i, a) in this.withIndex()) {
            for (j in (1 + i) until size) {
                result.add(a to this[j])
            }
        }
        return result
    }

    private fun List<String>.expandRows(): List<String> =
        flatMap {
            if (it.all { it == '.' })
                listOf(it, it)
            else
                listOf(it)
        }

    private fun List<String>.expandCols(): List<String> {
        val colsToExpand = this[0].mapIndexedNotNull { col, _ -> if (all { it[col] == '.' }) col else null }
        println(colsToExpand)
        return map {
            val chars = it.toMutableList()
            for ((i, col) in colsToExpand.withIndex()) {
                chars.add(col + i, '.')
            }

            chars.joinToString("")
        }
    }

}
