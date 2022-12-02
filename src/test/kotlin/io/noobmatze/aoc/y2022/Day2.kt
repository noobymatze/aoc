package io.noobmatze.aoc.y2022

import kotlin.test.Test
import kotlin.test.assertEquals

class Day2 {

    @Test
    fun test() {
        val patterns = mapOf(
            // DRAWS
            "A" to "X" to 4,
            "B" to "Y" to 5,
            "C" to "Z" to 6,
            // LOSES
            "A" to "Z" to 3,
            "B" to "X" to 1,
            "C" to "Y" to 2,
            // WINS
            "A" to "Y" to 8,
            "B" to "Z" to 9,
            "C" to "X" to 7,
        )

        val result = Aoc.getInput(2).lineSequence()
            .map { it.split(" ") }
            .sumOf { (enemy, me) -> patterns[enemy to me]!! }

        println(result)
    }

    @Test
    fun test2() {
        val patterns = mapOf(
            // LOSES
            "A" to "X" to 3,
            "B" to "X" to 1,
            "C" to "X" to 2,
            // DRAWS
            "A" to "Y" to 4,
            "B" to "Y" to 5,
            "C" to "Y" to 6,
            // WINS
            "A" to "Z" to 8,
            "B" to "Z" to 9,
            "C" to "Z" to 7,
        )

        val result = Aoc.getInput(2).lineSequence()
            .map { it.split(" ") }
            .sumOf { (enemy, me) -> patterns[enemy to me]!! }

        println(result)
    }

}