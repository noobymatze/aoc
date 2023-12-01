package io.noobymatze.aoc.y2023

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day1 {

    @Test
    fun test() {
        val result = Aoc.getInput(1)
            .lineSequence()
            .map { "${it.find { it.isDigit() }}${it.findLast { it.isDigit() }}".toInt() }
            .sum()

        println(result)
    }

    @Test
    fun test2() {
        val wordToDigit = mapOf(
            "one" to "1", "two" to "2", "three" to "3",
            "four" to "4", "five" to "5", "six" to "6",
            "seven" to "7", "eight" to "8", "nine" to "9",
        )

        fun String.findDigit(findHelp: String.(Collection<String>) -> Pair<Int, String>?): String =
            findHelp(wordToDigit.keys + wordToDigit.values)
                ?.let { (_, word) -> if (word in wordToDigit.values) word else wordToDigit[word] }!!

        val result = Aoc.getInput(1)
            .lineSequence()
            .map { "${it.findDigit(String::findAnyOf)}${it.findDigit(String::findLastAnyOf)}".toInt() }
            .sum()

        println(result)
    }

    // The first crazy thing I did, that worked, but was a huge pain:
    fun findLast(input: String): String {
        var i = input.length - 1
        while (i >= 0) {
            val a = input.getOrNull(i - 4)
            val b = input.getOrNull(i - 3)
            val c = input.getOrNull(i - 2)
            val d = input.getOrNull(i - 1)
            val e = input[i]
            when {
                e.isDigit() -> return "$e"
                c == 'o' && d == 'n' && e == 'e' -> return "1"
                c == 't' && d == 'w' && e == 'o' -> return "2"
                a == 't' && b == 'h' && c == 'r' && d == 'e' && e == 'e' -> return "3"
                b == 'f' && c == 'o' && d == 'u' && e == 'r' -> return "4"
                b == 'f' && c == 'i' && d == 'v' && e == 'e' -> return "5"
                c == 's' && d == 'i' && e == 'x'  -> return "6"
                a == 's' && b == 'e' && c == 'v' && d == 'e' && e == 'n' -> return "7"
                a == 'e' && b == 'i' && c == 'g' && d == 'h' && e == 't' -> return "8"
                b == 'n' && c == 'i' && d == 'n' && e == 'e' -> return "9"

                else -> i--
            }
        }

        return input
    }

    // one, two, three, four, five, six, seven, eight, nine
    fun findFirst(input: String): String {
        var i = 0
        while (i < input.length) {
            val a = input[i]
            val b = input.getOrNull(i + 1)
            val c = input.getOrNull(i + 2)
            val d = input.getOrNull(i + 3)
            val e = input.getOrNull(i + 4)
            when {
                a.isDigit() -> return "$a"
                a == 'o' && b == 'n' && c == 'e' -> return "1"
                a == 't' && b == 'w' && c == 'o' -> return "2"
                a == 't' && b == 'h' && c == 'r' && d == 'e' && e == 'e' -> return "3"
                a == 'f' && b == 'o' && c == 'u' && d == 'r' -> return "4"
                a == 'f' && b == 'i' && c == 'v' && d == 'e' -> return "5"
                a == 's' && b == 'i' && c == 'x'  -> return "6"
                a == 's' && b == 'e' && c == 'v' && d == 'e' && e == 'n' -> return "7"
                a == 'e' && b == 'i' && c == 'g' && d == 'h' && e == 't' -> return "8"
                a == 'n' && b == 'i' && c == 'n' && d == 'e' -> return "9"

                else -> i++
            }
        }

        return input
    }

}