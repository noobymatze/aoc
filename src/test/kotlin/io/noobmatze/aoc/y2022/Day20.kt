package io.noobmatze.aoc.y2022

import kotlin.math.abs
import kotlin.test.Test

class Day20 {

    data class N(
        val initialIndex: Int,
        val index: Int,
        val value: Int,
    )

    @Test
    fun test1() {
        val example = """
            |1
            |2
            |-3
            |3
            |-2
            |0
            |4
        """.trimMargin()

        val data = example // Aoc.getInput(20)
            .lineSequence()
            .mapIndexed { index, n -> N(index, index, n.toInt()) }
            .toList().toTypedArray()

        for (i in data.indices) {
            val next = data.find { it.initialIndex == i }
        }

        println(data)
    }

    @Test
    fun test2() {
    }

}