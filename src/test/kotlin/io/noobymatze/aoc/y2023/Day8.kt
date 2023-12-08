package io.noobymatze.aoc.y2023

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day8 {

    @Test
    fun test() {
        val (movement, nodesString) = Aoc.getInput(8)
            .split("\n\n")

        val map = nodesString.lines().associate {
            val (start, target) = it.split(" = ")
            val (left, right) = target.trim('(', ')').split(", ")
            start to (left to right)
        }

        var steps = 0
        var node = "AAA"
        while (node != "ZZZ") {
            val step = movement[steps % movement.length]
            if (step == 'R')
                node = map[node]!!.second
            else if (step == 'L')
                node = map[node]!!.first
            steps++
        }

        println(steps)
    }

    @Test
    fun test2() {
        val (movement, nodesString) = Aoc.getInput(8)
            .split("\n\n")

        val map: Map<String, Pair<String, String>> = nodesString.lines().associate {
            val (start, target) = it.split(" = ")
            val (left, right) = target.trim('(', ')').split(", ")
            start to (left to right)
        }

        // Find the steps to the first node ending in Z for each starting node
        val steps = map.keys.filter { it.endsWith("A") }.map {
            var steps = 0L
            var node = it
            while (!node.endsWith("Z")) {
                val step = movement[(steps % movement.length).toInt()]
                if (step == 'R')
                    node = map[node]!!.second
                else if (step == 'L')
                    node = map[node]!!.first
                steps++
            }
            steps
        }

        // Once a node ending in Z is reached, it takes the same
        // amount of steps to reach it again, therefore
        // compute the least common denominator
        println(steps.reduce { a, b -> lcm(a, b) })
    }

    private fun lcm(a: Long, b: Long): Long =
        (a * b) / gcm(a, b)

    private tailrec fun gcm(a: Long, b: Long): Long =
        if (b == 0L) a else gcm(b, a % b)

}
