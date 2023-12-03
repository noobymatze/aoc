package io.noobymatze.aoc.y2023

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day3 {

    data class Pos(
        val row: Int,
        val col: Int,
    ) {
        val left: Pos get() = Pos(row, col - 1)
        val right: Pos get() = Pos(row, col + 1)
        val bottom: Pos get() = Pos(row + 1, col)
        val top: Pos get() = Pos(row - 1, col)
    }

    @Test
    fun test() {
        val input = Aoc.getInput(3)
            .lines()

        val numbers = mutableListOf<Int>()
        for ((row, line) in input.withIndex()) {
            for ((col, c) in line.withIndex()) {
                if (!c.isDigit() && c != '.') {
                    // Top
                    val top = row - 1
                    if (top >= 0 && input[top][col].isDigit()) {
                        val r = top
                        var start = col
                        while (start - 1 >= 0 && input[r][start - 1].isDigit()) start--
                        var end = col
                        while (end + 1 < input[r].length && input[r][end + 1].isDigit()) end++
                        numbers.add(input[r].substring(start, end + 1).toInt())
                    }

                    // Top left
                    if (top >= 0 && col - 1 >= 0 && col + 1 < input[top].length && !input[top][col].isDigit() && input[top][col - 1].isDigit()) {
                        val r = top
                        var start = col
                        while (start - 1 >= 0 && input[r][start - 1].isDigit()) start--
                        numbers.add(input[r].substring(start, col).toInt())
                    }

                    // Top Right
                    if (top >= 0 && col - 1 >= 0 && col + 1 < input[top].length && !input[top][col].isDigit() && input[top][col + 1].isDigit()) {
                        val r = top
                        var end = col
                        while (end + 1 < input[r].length && input[r][end + 1].isDigit()) end++
                        numbers.add(input[r].substring(col + 1, end + 1).toInt())
                    }

                    // Left
                    if (col - 1 >= 0 && input[row][col - 1].isDigit()) {
                        var start = col - 1
                        while (start - 1 >= 0 && input[row][start - 1].isDigit()) start--
                        numbers.add(input[row].substring(start, col).toInt())
                    }

                    // Right
                    if (col + 1 < input[row].length && input[row][col + 1].isDigit()) {
                        var end = col + 1
                        while (end + 1 < input[row].length && input[row][end + 1].isDigit()) end++
                        numbers.add(input[row].substring(col + 1, end + 1).toInt())
                    }

                    // Bottom
                    val bottom = row + 1
                    if (row + 1 < input.size && input[row + 1][col].isDigit()) {
                        val r = row + 1
                        var start = col
                        while (start - 1 >= 0 && input[r][start - 1].isDigit()) start--
                        var end = col
                        while (end + 1 < input[r].length && input[r][end + 1].isDigit()) end++
                        numbers.add(input[r].substring(start, end + 1).toInt())
                    }

                    // Bottom left
                    if (bottom < input.size && col - 1 >= 0 && col + 1 < input[bottom].length && !input[bottom][col].isDigit() && input[bottom][col - 1].isDigit()) {
                        val r = bottom
                        var start = col
                        while (start - 1 >= 0 && input[r][start - 1].isDigit()) start--
                        numbers.add(input[r].substring(start, col).toInt())
                    }

                    // Bottom Right
                    if (bottom < input.size && col - 1 >= 0 && col + 1 < input[bottom].length && !input[bottom][col].isDigit() && input[bottom][col + 1].isDigit()) {
                        val r = bottom
                        var end = col
                        while (end + 1 < input[r].length && input[r][end + 1].isDigit()) end++
                        numbers.add(input[r].substring(col + 1, end + 1).toInt())
                    }

                }
            }
        }

        println(numbers.sum())

    }

    @Test
    fun test2() {
        val input = Aoc.getInput(3)
            .lines()

        val numbers = mutableListOf<List<Long>>()
        for ((row, line) in input.withIndex()) {
            for ((col, c) in line.withIndex()) {
                if (c == '*') {
                    val gearNumbers = mutableListOf<Long>()
                    // Top
                    val top = row - 1
                    if (top >= 0 && input[top][col].isDigit()) {
                        val r = top
                        var start = col
                        while (start - 1 >= 0 && input[r][start - 1].isDigit()) start--
                        var end = col
                        while (end + 1 < input[r].length && input[r][end + 1].isDigit()) end++
                        gearNumbers.add(input[r].substring(start, end + 1).toLong())
                    }

                    // Top left
                    if (top >= 0 && col - 1 >= 0 && col + 1 < input[top].length && !input[top][col].isDigit() && input[top][col - 1].isDigit()) {
                        val r = top
                        var start = col
                        while (start - 1 >= 0 && input[r][start - 1].isDigit()) start--
                        gearNumbers.add(input[r].substring(start, col).toLong())
                    }

                    // Top Right
                    if (top >= 0 && col - 1 >= 0 && col + 1 < input[top].length && !input[top][col].isDigit() && input[top][col + 1].isDigit()) {
                        val r = top
                        var end = col
                        while (end + 1 < input[r].length && input[r][end + 1].isDigit()) end++
                        gearNumbers.add(input[r].substring(col + 1, end + 1).toLong())
                    }

                    // Left
                    if (col - 1 >= 0 && input[row][col - 1].isDigit()) {
                        var start = col - 1
                        while (start - 1 >= 0 && input[row][start - 1].isDigit()) start--
                        gearNumbers.add(input[row].substring(start, col).toLong())
                    }

                    // Right
                    if (col + 1 < input[row].length && input[row][col + 1].isDigit()) {
                        var end = col + 1
                        while (end + 1 < input[row].length && input[row][end + 1].isDigit()) end++
                        gearNumbers.add(input[row].substring(col + 1, end + 1).toLong())
                    }

                    // Bottom
                    val bottom = row + 1
                    if (row + 1 < input.size && input[row + 1][col].isDigit()) {
                        val r = row + 1
                        var start = col
                        while (start - 1 >= 0 && input[r][start - 1].isDigit()) start--
                        var end = col
                        while (end + 1 < input[r].length && input[r][end + 1].isDigit()) end++
                        gearNumbers.add(input[r].substring(start, end + 1).toLong())
                    }

                    // Bottom left
                    if (bottom < input.size && col - 1 >= 0 && col + 1 < input[bottom].length && !input[bottom][col].isDigit() && input[bottom][col - 1].isDigit()) {
                        val r = bottom
                        var start = col
                        while (start - 1 >= 0 && input[r][start - 1].isDigit()) start--
                        gearNumbers.add(input[r].substring(start, col).toLong())
                    }

                    // Bottom Right
                    if (bottom < input.size && col - 1 >= 0 && col + 1 < input[bottom].length && !input[bottom][col].isDigit() && input[bottom][col + 1].isDigit()) {
                        val r = bottom
                        var end = col
                        while (end + 1 < input[r].length && input[r][end + 1].isDigit()) end++
                        gearNumbers.add(input[r].substring(col + 1, end + 1).toLong())
                    }
                    numbers.add(gearNumbers)
                }
            }
        }

        println(numbers.filter { it.size == 2 }.sumOf { it.reduce { a, b -> a * b } })
    }

}