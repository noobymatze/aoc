package io.noobmatze.aoc.y2022

import kotlin.test.Test

class Day9 {

    data class Pos(var row: Int, var col: Int) {
        infix fun isAdjacent(other: Pos): Boolean =
            // Same spot
            this == other
                // Down
                || (row + 1 == other.row && col == other.col)
                // Bottom Left
                || (row + 1 == other.row && col - 1 == other.col)
                // Bottom Right
                || (row + 1 == other.row && col + 1 == other.col)
                // Top
                || (row - 1 == other.row && col == other.col)
                // Top Left
                || (row - 1 == other.row && col - 1 == other.col)
                // Top Right
                || (row - 1 == other.row && col + 1 == other.col)
                // Left
                || (row == other.row && col - 1 == other.col)
                // Right
                || (row == other.row && col + 1 == other.col)
    }

    @Test
    fun test() {
        val data = Aoc.getInput(9)
            .lineSequence().map { it.split(" ").let { (d, n) -> d to n.toInt() } }

        val head = Pos(0, 0)
        val tail = Pos(0, 0)
        val visited = mutableSetOf(0 to 0)
        for ((move, n) in data) {
            for (x in 0 until n) {
                when (move) {
                    "U" -> head.row--
                    "D" -> head.row++
                    "R" -> head.col++
                    "L" -> head.col--
                }

                move(head, tail)
                visited.add(tail.row to tail.col)
            }
        }

        println(visited.size)
    }

    @Test
    fun test2() {
        val data = Aoc.getInput(9)
            .lineSequence().map { it.split(" ").let { (d, n) -> d to n.toInt() } }

        val snake = Array(10) { Pos(0, 0) }
        val visited = mutableSetOf(0 to 0)
        for ((move, n) in data) {
            for (x in 0 until n) {
                when (move) {
                    "U" -> snake[0].row--
                    "D" -> snake[0].row++
                    "R" -> snake[0].col++
                    "L" -> snake[0].col--
                }

                for (i in 1 until snake.size) {
                    move(snake[i - 1], snake[i])
                }
                visited.add(snake.last().row to snake.last().col)
            }
        }

        println(visited.size)
    }

    private fun move(head: Pos, tail: Pos): Unit = when {
        head isAdjacent tail -> Unit

        else -> {
            tail.row = when {
                head.row < tail.row -> tail.row - 1
                head.row > tail.row -> tail.row + 1
                else -> tail.row
            }
            tail.col = when {
                head.col < tail.col -> tail.col - 1
                head.col > tail.col -> tail.col + 1
                else -> tail.col
            }
        }
    }

}