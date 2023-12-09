package io.noobymatze.aoc.y2023

import io.noobymatze.aoc.Aoc
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.test.Test

class Day9 {

    @Test
    fun test() {
        val result = Aoc.getInput(9)
            .lineSequence()
            .map { it.trim().split(" ").map { it.toInt() } }
            .map {
                val values = mutableListOf(it.toMutableList())
                while (!values.last().all { it == 0 }) {
                    val list = values.last().zipWithNext().map {
                        it.second - it.first
                    }.toMutableList()
                    values.add(list)
                }

                values.removeLast()
                var v = 0L
                var i = values.size - 1
                while (i >= 0) {
                    v += values[i].last()
                    i--
                }
                v
            }
            .toList()

        println(result.sum())
    }

    @Test
    fun test2() {
        val result = Aoc.getInput(9)
            .lineSequence()
            .map { it.trim().split(" ").map { it.toInt() } }
            .map {
                val values = mutableListOf(it.toMutableList())
                while (!values.last().all { it == 0 }) {
                    val list = values.last().zipWithNext().map {
                        it.first - it.second
                    }.toMutableList()
                    values.add(list)
                }

                values.removeLast()
                var v = 0L
                var i = values.size - 1
                while (i >= 0) {
                    v += values[i].first()
                    i--
                }
                v
            }
            .toList()

        println(result.sum())
    }

}
