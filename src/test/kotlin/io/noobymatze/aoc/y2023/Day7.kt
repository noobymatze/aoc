package io.noobymatze.aoc.y2023

import io.noobymatze.aoc.Aoc
import kotlin.math.exp
import kotlin.test.Test

class Day7 {

    fun compareCards(cards: String, a: String, b: String): Int {
        var result = 0
        for (i in 0..5)  {
            result = cards.indexOf(a[i]) - cards.indexOf(b[i])
            if (result != 0) return result
        }

        return result
    }

    @Test
    fun test() {
        fun strength(hand: String): Int {
            val freq = hand.groupingBy { it }.eachCount()
            return when {
                // Five of a kind
                freq.size == 1 -> 7
                // Four of a kind
                freq.any { it.value == 4 } -> 6
                // Full house
                freq.any { it.value == 3 } && freq.any { it.value == 2 } -> 5
                // Three of a kind
                freq.size == 3 && freq.any { it.value == 3 } -> 4
                // Two pair
                freq.count { it.value == 2 } == 2 -> 3
                // One pair
                freq.size == 4 && freq.count { it.value == 2 } == 1 -> 2
                // High card
                else -> 1
            }
        }

        val cards = "23456789TJQKA"
        val result = Aoc.getInput(7)
            .lines()
            .map { it.split(" ") }
            .sortedWith(compareBy<List<String>> { (hand, _) -> strength(hand) }
                .then { (a, _), (b, _) -> compareCards(cards, a, b) }
            )
            .mapIndexed { i, (_, bid) -> bid.toInt() * (i + 1) }
            .sum()

        println(result)
    }

    @Test
    fun test2() {
        val cards = "J23456789TQKA"

        fun strength(hand: String): Int {
            val freq = hand.groupingBy { it }.eachCount()
            val jokers = freq['J'] ?: 0
            return when {
                // Five of a kind
                freq.size == 1 || freq.any { it.key != 'J' && it.value + jokers == 5 } -> 7
                // Four of a kind
                freq.count { it.value == 4 } == 1
                    || freq.any { it.key != 'J' && it.value + jokers == 4 } -> 6
                // Full house
                freq.any { it.value == 3 } && freq.any { it.value == 2 }
                    || (jokers == 2 && freq.count { it.key != 'J' } == 2)
                    || (jokers == 1 && freq.count { it.key != 'J' } == 2) -> 5
                // Three of a kind
                freq.count { it.value == 3 } == 1
                    || freq.any { it.key != 'J' && it.value + jokers == 3 } -> 4
                // Two pair
                freq.count { it.value == 2 } == 2 -> 3
                // One pair
                freq.count { it.value == 2 } == 1
                    || (freq.any { it.key != 'J' && it.value + jokers == 2 }) -> 2
                // High card
                else -> 1
            }
        }

        val result = Aoc.getInput(7)
            .lines()
            .map { it.split(" ") }
            .sortedWith(compareBy<List<String>> { (hand, _) -> strength(hand) }
                .then { (a, _), (b, _) -> compareCards(cards, a, b) }
            )
            .mapIndexed { i, (_, bid) -> bid.toInt() * (i + 1) }
            .sum()

        println(result)
    }

}