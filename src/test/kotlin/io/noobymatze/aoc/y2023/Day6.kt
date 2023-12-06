package io.noobymatze.aoc.y2023

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day6 {

    private val intRegex = Regex("\\d+")

    @Test
    fun test() {
        val (time, distance) = Aoc.getInput(6)
            .lines()
            .map { v -> intRegex.findAll(v).map { it.value.toInt() } }

        val result = time.zip(distance).map { (t, d) ->
            (1 until t).count { s -> (t - s) * s > d }
        }.reduce { a, b -> a * b }

        println(result)
    }

    @Test
    fun test2() {
        val (time, distance) = Aoc.getInput(6)
            .lines()
            .map { v -> intRegex.findAll(v).joinToString("") { it.value }.toLong() }

        val result = (1 until time)
            .count { speed -> (time - speed) * speed > distance }

        println(result)
    }

}