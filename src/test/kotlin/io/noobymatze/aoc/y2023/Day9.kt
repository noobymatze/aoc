package io.noobymatze.aoc.y2023

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day9 {

    @Test
    fun test() {
        val result = Aoc.getInput(9)
            .lineSequence()
            .map { it.trim().split(" ").map { it.toInt() } }
            .map { history ->
                generateSequence(history) { it.zipWithNext { a, b -> b - a } }
                    .takeWhile { it.any { it != 0 } }
                    .sumOf { it.last() }
            }
            .sum()

        println(result)
    }

    @Test
    fun test2() {
        val result = Aoc.getInput(9)
            .lineSequence()
            .map { it.trim().split(" ").map { it.toInt() } }
            .map { history ->
                generateSequence(history) { it.zipWithNext { a, b -> a - b } }
                    .takeWhile { it.any { it != 0 } }
                    .sumOf { it.first() }
            }
            .sum()

        println(result)
    }

}
