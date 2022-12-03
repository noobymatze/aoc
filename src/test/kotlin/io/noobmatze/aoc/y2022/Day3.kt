package io.noobmatze.aoc.y2022

import kotlin.test.Test

class Day3 {

    private val alphabet = ""

    @Test
    fun test() {
        val result = Aoc.getInput(3).lineSequence()
            .flatMap {
                val index = it.length / 2
                it.take(index).toSet() intersect it.drop(index).toSet()
            }
            .sumOf { if (it.isUpperCase()) it.code - (64 - 26) else it.code - 96 }

        println(result)
    }

    @Test
    fun test2() {
        val result = Aoc.getInput(3).lineSequence()
            .windowed(size = 3, step = 3)
            .flatMap {
                it.map { it.toSet() }.reduce(Set<Char>::intersect)
            }
            .sumOf { if (it.isUpperCase()) it.code - (64 - 26) else it.code - 96 }

        println(result)
    }

}