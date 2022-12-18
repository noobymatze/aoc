package io.noobmatze.aoc.y2022

import io.noobmatze.aoc.y2022.Aoc.symmetricDifference
import kotlin.test.Test

class Day18 {

    data class Pos(val x: Int, val y: Int, val z: Int) {
        val sides get(): Set<Set<Pos>> {
            val front = setOf(
                this,
                Pos(x + 1, y, z),
                Pos(x, y + 1, z),
                Pos(x + 1, y + 1, z),
            )

            val back = front.map { it.copy(z = it.z + 1) }.toSet()

            val left = setOf(
                this,
                Pos(x, y, z + 1),
                Pos(x, y + 1, z),
                Pos(x, y + 1, z + 1),
            )
            val right = left.map { it.copy(x = it.x + 1) }.toSet()
            val bottom = setOf(
                this,
                Pos(x + 1, y, z),
                Pos(x, y, z + 1),
                Pos(x + 1, y, z + 1),
            )
            val top = bottom.map { it.copy(y = it.y + 1) }.toSet()

            return setOf(front, back, left, right, top, bottom)
        }
    }

    @Test
    fun test1() {
        val result = Aoc.getInput(18)
            .lineSequence()
            .map { line -> line.split(",").map(String::toInt) }
            .map { (x, y, z) -> Pos(x, y, z) }
            // Idea: Build a set of all sides of a cube and compute
            // the symmetric set difference
            .map { it.sides }
            .reduce { a, b -> a symmetricDifference b }

        println(result.size)
    }

    @Test
    fun test2() {
        val example = """
            |2,2,2
            |1,2,2
            |3,2,2
            |2,1,2
            |2,3,2
            |2,2,1
            |2,2,3
            |2,2,4
            |2,2,6
            |1,2,5
            |3,2,5
            |2,1,5
            |2,3,5
        """.trimMargin()

        val cubes = Aoc.getInput(18)
            .lineSequence()
            .map { line -> line.split(",").map(String::toInt) }
            .map { (x, y, z) -> Pos(x, y, z) }
            .toList()

        val result = cubes.map { it.sides }.reduce { a, b -> a symmetricDifference b }

        println(result.size)
    }

}