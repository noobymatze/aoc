package io.noobmatze.aoc.y2022

import java.util.PriorityQueue
import kotlin.test.Test

class Day12 {

    data class Pos(val row: Int, val col: Int)
    val Pos.neighbors: List<Pos> get() = listOf(
        Pos(row + 1, col),
        Pos(row - 1, col),
        Pos(row, col + 1),
        Pos(row, col - 1),
    )

    @Test
    fun test() {
        var start = Pos(0, 0)
        var end = Pos(0, 0)
        val data = mutableMapOf<Pos, Int>()
        Aoc.getInput(12).lineSequence().forEachIndexed { row, it ->
            it.forEachIndexed { col, it ->
                val c = when (it) {
                    'E' -> {
                        end = Pos(row, col)
                        'z'
                    }
                    'S' -> {
                        start = Pos(row, col)
                        'a'
                    }
                    else -> it
                }

                data[Pos(row, col)] = c.code - 97
            }
        }

        val visited = mutableSetOf<Pos>()
        val frontier = mutableListOf(start)
        val paths = mutableSetOf<MutableList<Pos>>()
        while (frontier.isNotEmpty()) {
            val node = frontier.removeFirst()
            if (node in visited || node !in data || node == end) continue
            node.neighbors.forEach {
                frontier.add(it)
            }
        }
    }

    @Test
    fun test2() {
    }

}