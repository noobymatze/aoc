package io.noobmatze.aoc.y2022

import java.util.PriorityQueue
import kotlin.test.Test

class Day12 {

    data class Pos(val row: Int, val col: Int)
    fun Pos.neighbors(): List<Pos> = listOf(
        Pos(row + 1, col),
        Pos(row - 1, col),
        Pos(row, col + 1),
        Pos(row, col - 1),
    )

    @Test
    fun test() {
        var start = Pos(0, 0)
        var end = Pos(0, 0)
        val data = Aoc.getInput(12).lineSequence().flatMapIndexed { row, it ->
            it.mapIndexed { col, it ->
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
                Pos(row, col) to c.code - 97
            }
        }.toMap()

        val visited = mutableSetOf<Pos>()
        val frontier = mutableListOf(start)
        val path = mutableMapOf<Pos, Pos>()
        while (frontier.isNotEmpty()) {
            val node = frontier.removeFirst()
            if (node in visited || node !in data || node == end) continue
            node.neighbors().filter { it in data && data[it]!! <= data[node]!! + 1 }.forEach {
                frontier.add(it)
            }
        }
    }

    @Test
    fun test2() {
    }

}