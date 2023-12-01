package io.noobymatze.aoc.y2022

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day19 {

    enum class Type {
        Ore,
        Clay,
        Obsidian,
        Geode,

    }

    data class Blueprint(
        val oreRobot: Int,
        val clayRobot: Int,
        val obsidianRobot: Pair<Int, Int>,
        val geodeRobot: Pair<Int, Int>,
    )

    @JvmInline
    value class Robot(
        val mining: Type,
    )

    @Test
    fun test1() {
        val regex = Regex("\\d+")
        val data = Aoc.getInput(19)
            .lineSequence()
            .map { line ->
                val robots = regex.findAll(line)
                    .map { it.value.toInt() }
                    .toList()
                Blueprint(robots[0], robots[1], robots[2] to robots[3], robots[4] to robots[5])
            }
            .toList()

        val resources = mutableMapOf<Type, Int>()
        val robots = mutableListOf(Robot(Type.Ore))
        var minute = 0
        while (minute < 25) {
            val newRobots = mutableListOf<Robot>()

            robots.forEach {
                resources.compute(it.mining) { _, value -> value?.plus(1) ?: 1 }
            }

            robots.addAll(newRobots)
            minute++
        }

        println(data)
    }

    @Test
    fun test2() {
    }

}