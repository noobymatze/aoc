package io.noobymatze.aoc.y2023

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day4 {

    @Test
    fun test() {
        val result = Aoc.getInput(4)
            .lineSequence()
            .map {
                val (card, values) = it.split(": ")
                val (winning, numbers) = values.split(" | ")
                    .map { it.split(" ").mapNotNull { it.toIntOrNull() } }
                winning.toSet() to numbers
            }
            .map { (winning, numbers) ->
                numbers.fold(0) { result, next ->
                    if (result == 0 && next in winning)
                        1
                    else if (next in winning)
                        result * 2
                    else
                        result
                }
            }
            .sum()

        println(result)
    }

    @Test
    fun test2() {
        val cards = Aoc.getInput(4)
            .lineSequence()
            .map {
                val (card, values) = it.split(": ")
                val (winning, numbers) = values.split(" | ")
                    .map { it.split(" ").mapNotNull { it.toIntOrNull() } }
                val cardNumber = card.takeLastWhile { it.isDigit() }.toInt()
                val winningSet = winning.toSet()
                cardNumber to numbers.count { it in winningSet }
            }
            .toList()

        val map = cards.associate { it.first to 1 }.toMutableMap()
        for ((card, winningCount) in cards) {
            for (i in 1..map[card]!!)  {
                val start = card + 1
                val end = start + winningCount
                for (k in start until end) {
                    if (k in map)
                        map[k] = map[k]!! + 1
                }
            }
        }

        println(map.values.sum())
    }

}