package io.noobmatze.aoc.y2022

import io.noobmatze.aoc.y2022.Aoc.paired
import java.util.Stack
import kotlin.test.Test

class Day5 {

    @Test
    fun test() {
        val (stackInput, moves) = Aoc.getInput(5).split("\n\n")
        val (stackLines,  lastLine) = stackInput.lines().let { it.dropLast(1) to it.last() }
        val stacks = ('1'..'9')
            .map { lastLine.indexOf(it) }
            .map {  index -> stackLines.mapNotNull { it.getOrNull(index)?.takeIf { it != ' ' } } }
            .map { ArrayDeque(it) }

        val instructions = moves.lines()
            .map { Regex("\\d+").findAll(it).map { it.value.toInt() }.toList() }

        instructions.forEach { (n, from, to) ->
            repeat(n) {
                stacks[to - 1].addFirst(stacks[from - 1].removeFirst())
            }
        }

        println(stacks.joinToString("") { "${it[0]}" })
    }

    @Test
    fun test2() {
        val (stackInput, moves) = Aoc.getInput(5).split("\n\n")
        val (stackLines,  lastLine) = stackInput.lines().let { it.dropLast(1) to it.last() }
        val stacks = ('1'..'9')
            .map { lastLine.indexOf(it) }
            .map {  index -> stackLines.mapNotNull { it.getOrNull(index)?.takeIf { it != ' ' } } }
            .map { ArrayDeque(it) }

        val instructions = moves.lines()
            .map { Regex("\\d+").findAll(it).map { it.value.toInt() }.toList() }

        instructions.forEach { (n, from, to) ->
            val result = List(n) { stacks[from - 1].removeFirst() }
            result.reversed().forEach { stacks[to - 1].addFirst(it) }
        }

        println(stacks.joinToString("") { "${it[0]}" })
    }

}