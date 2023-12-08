package io.noobymatze.aoc.y2023

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day8 {

    @Test
    fun test() {
        val test = """
            RL

            AAA = (BBB, CCC)
            BBB = (DDD, EEE)
            CCC = (ZZZ, GGG)
            DDD = (DDD, DDD)
            EEE = (EEE, EEE)
            GGG = (GGG, GGG)
            ZZZ = (ZZZ, ZZZ)
        """.trimIndent()
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
        val test = """
            LR

            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)
        """.trimIndent()
        val (movement, nodesString) = Aoc.getInput(8)
            .split("\n\n")

        val map: Map<String, Pair<String, String>> = nodesString.lines().associate {
            val (start, target) = it.split(" = ")
            val (left, right) = target.trim('(', ')').split(", ")
            start to (left to right)
        }

        var steps = 0L
        val nodes = map.keys.filter { it.endsWith("A") }.toMutableList()
        while (!nodes.all { it.endsWith("Z") }) {
            val step = movement[(steps % movement.length).toInt()]
            for ((i, node) in nodes.withIndex()) {
                if (step == 'R')
                    nodes[i] = map[node]!!.second
                else if (step == 'L')
                    nodes[i] = map[node]!!.first
            }
            steps++
        }

        println(steps)
    }

}
