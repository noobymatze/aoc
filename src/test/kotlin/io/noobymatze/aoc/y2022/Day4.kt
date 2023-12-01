package io.noobymatze.aoc.y2022

import io.noobymatze.aoc.Aoc
import io.noobymatze.aoc.Aoc.paired
import kotlin.test.Test

class Day4 {

    operator fun Pair<Int, Int>.contains(b: Pair<Int, Int>): Boolean =
        first <= b.first && b.second <= second

    infix fun Pair<Int, Int>.overlaps(b: Pair<Int, Int>): Boolean =
        (first <= b.first && b.first <= second)
            || (b.first <= first && first <= b.second)

    @Test
    fun test() {
        val result = Aoc.getInput(4).lineSequence()
            .map { line -> line.paired(",") { it.paired("-", String::toInt) } }
            .count { (a, b) -> a in b || b in a }

        println(result)
    }

    @Test
    fun test2() {
        val result = Aoc.getInput(4).lineSequence()
            .map { line -> line.paired(",") { it.paired("-", String::toInt) } }
            .count { (a, b) -> a overlaps b }

        println(result)
    }

}