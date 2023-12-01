package io.noobymatze.aoc.y2022

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day6 {

    @Test
    fun test() {
        val result = Aoc.getInput(6).asSequence()
            .withIndex()
            .windowed(4)
            .find { it.map { it.value }.toSet().size == 4 }!!

        println(result[0].index + 4)
    }

    @Test
    fun test2() {
        val result = Aoc.getInput(6).asSequence()
            .withIndex()
            .windowed(14)
            .find { it.map { it.value }.toSet().size == 14 }!!

        println(result[0].index + 14)
    }

}