package io.noobmatze.aoc.y2022

import io.noobmatze.aoc.y2022.Aoc.findAllByGroup
import kotlin.math.abs
import kotlin.math.max
import kotlin.test.Test

class Day16 {

    @Test
    fun test() {
        val example = """
            |Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
            |Valve BB has flow rate=13; tunnels lead to valves CC, AA
            |Valve CC has flow rate=2; tunnels lead to valves DD, BB
            |Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
            |Valve EE has flow rate=3; tunnels lead to valves FF, DD
            |Valve FF has flow rate=0; tunnels lead to valves EE, GG
            |Valve GG has flow rate=0; tunnels lead to valves FF, HH
            |Valve HH has flow rate=22; tunnel leads to valve GG
            |Valve II has flow rate=0; tunnels lead to valves AA, JJ
            |Valve JJ has flow rate=21; tunnel leads to valve II
        """.trimMargin()
        val rateR = Regex("=(?<rate>\\d+)")
        val valvesR = Regex("(?<valve>[A-Z][A-Z])")
        val rates = mutableMapOf<String, Int>()
        val valves = mutableMapOf<String, List<String>>()
        example // Aoc.getInput(16)
            .lineSequence()
            .forEach {
                val (rate) = rateR.findAllByGroup(it, "rate", String::toInt).toList()
                val foundValves = valvesR.findAllByGroup(it, "valve", {it}).toList()
                val valve = foundValves[0]
                rates[valve] = rate
                valves[valve] = foundValves.drop(1)
            }

        val distances = mutableMapOf<Pair<>>()

        println("     " + valves.map { it.key }.joinToString(" | "))
        for ((key, value) in valves) {
            println("$key | ")
        }

        valves.map { it.key }
            .filter { rates[it]!! > 0 }

    }

    @Test
    fun test2() {
    }

}