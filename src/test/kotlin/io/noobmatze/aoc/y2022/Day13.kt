package io.noobmatze.aoc.y2022

import io.noobmatze.aoc.y2022.Day13.Order.*
import kotlin.test.Test

class Day13 {

    @Test
    fun test() {
        val result = Aoc.getInput(13)
            .split("\n\n")
            .asSequence()
            .map { it.lines().map { parse(tokenize(it)) as Expr.Seq } }
            .mapIndexed { index, (a, b) -> index to a.compare(b) }
            .filter { it.second == LT }
            .sumOf { it.first + 1 }

        println(result)
    }

    @Test
    fun test2() {
        val result = (Aoc.getInput(13) + "\n\n[[2]]\n[[6]]")
            .split("\n\n")
            .asSequence()
            .flatMap { it.lines().map { parse(tokenize(it)) as Expr.Seq } }
            .sortedWith { a, b -> a.compare(b).value }
            .withIndex()
            .toList()
            .filter { it.value.stringify() == "[[6]]" || it.value.stringify() == "[[2]]" }
            .map { it.index + 1 }
            .reduce { a, b -> a * b }

        println(result)
    }

    enum class Order(val value: Int) { LT(-1), EQ(0), GT(1) }

    sealed interface Expr {
        data class Number(val value: Int): Expr
        data class Seq(val expressions: List<Expr>): Expr

        fun stringify(): String = when (this) {
            is Number -> value.toString()
            is Seq -> "[${expressions.joinToString(",") { it.stringify() }}]"
        }

        fun compare(right: Expr): Order = when {
            this is Number && right is Number -> when {
                value < right.value -> LT
                value > right.value -> GT
                else -> EQ
            }

            this is Seq && right is Seq -> {
                val rest = expressions.zip(right.expressions)
                    .map { (a, b) -> a.compare(b) }
                    .dropWhile { it == EQ }

                when {
                    rest.isNotEmpty() -> rest[0]
                    expressions.size < right.expressions.size -> LT
                    expressions.size == right.expressions.size -> EQ
                    else -> GT
                }
            }

            this is Seq && right is Number ->
                compare(Seq(listOf(right)))

            this is Number && right is Seq ->
                Seq(listOf(this)).compare(right)

            else -> throw IllegalArgumentException()
        }

    }

    private fun tokenize(input: String): MutableList<String> =
        input.replace("[", " [ ")
            .replace("]", " ] ")
            .replace(",", " ")
            .split(" ")
            .filter { it.isNotBlank() }.toMutableList()

    private fun parse(tokens: MutableList<String>): Expr {
        if (tokens.isEmpty()) throw IllegalArgumentException()
        return when (val next = tokens.removeFirst()) {
            "[" -> {
                val expressions = mutableListOf<Expr>()
                while (tokens[0] != "]") {
                    expressions.add(parse(tokens))
                }
                tokens.removeFirst() // ']'
                Expr.Seq(expressions)
            }

            else ->
                Expr.Number(next.toInt())
        }
    }

}