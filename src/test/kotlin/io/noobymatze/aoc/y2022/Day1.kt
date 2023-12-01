package io.noobymatze.aoc.y2022

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day1 {

    @Test
    fun test() {
        val result = Aoc.getInput(1).split("\n\n")
            .maxOf { it.lines().sumOf(String::toInt) }

        println(result)
    }

    @Test
    fun test2() {
        val result = Aoc.getInput(1).split("\n\n")
            .map { it.lines().sumOf(String::toInt) }
            .sortedDescending()
            .take(3)

        println(result.sum())
    }

}