package io.noobymatze.aoc.y2023

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day2 {

    data class Game(
        val id: Int,
        val draws: List<Draw>,
    )

    data class Draw(
        val sets: List<Cubes>,
    )

    data class Cubes(
        val amount: Int,
        val color: String,
    )

    @Test
    fun test() {
        val bag = mapOf("red" to 12, "green" to 13, "blue" to 14)
        val result = Aoc.getInput(2)
            .lineSequence()
            .map { parse(it) }
            .filter { it.draws.all { it.sets.all { it.amount <= bag[it.color]!! } } }
            .sumOf { it.id }

        println(result)
    }

    @Test
    fun test2() {
        val result = Aoc.getInput(2)
            .lineSequence()
            .map { parse(it) }
            .map { game ->
                game.draws.flatMap { it.sets }
                    .groupBy { it.color }
                    .mapNotNull { (_, values) -> values.maxOfOrNull { it.amount } }
                    .reduce { a, b -> a * b }
            }
            .sum()

        println(result)
    }

    private fun parse(line: String): Game {
        val (game, drawsString) = line.split(": ")
        val gameId = game.takeLastWhile { it.isDigit() }.toInt()
        val draws = drawsString.split("; ").map {
            Draw(it.split(", ").map {
                val (amount, color) = it.split(" ")
                Cubes(amount.trim().toInt(), color.trim())
            })
        }

        return Game(gameId, draws)
    }

}