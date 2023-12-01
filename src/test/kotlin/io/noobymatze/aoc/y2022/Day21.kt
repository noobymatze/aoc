package io.noobymatze.aoc.y2022

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day21 {

    sealed interface Expr {
        data class BinOp(val op: String, val left: Expr, val right: Expr): Expr
        data class Var(val name: String): Expr
        data class Value(val value: Long): Expr

        fun eval(env: Map<String, Expr>): Long = when (this) {
            is BinOp -> {
                val a = left.eval(env)
                val b = right.eval(env)

                when (op) {
                    "+" -> a + b
                    "-" -> a - b
                    "*" -> a * b
                    "/" -> a / b
                    else -> throw IllegalArgumentException("Unknown $op")
                }
            }

            is Var -> env[name]!!.eval(env)
            is Value -> value
        }

        companion object {
            fun parse(input: String): Expr =
                if (input.all { it.isDigit() }) {
                    Value(input.toLong())
                }
                else {
                    val (left, op, right) = input.split(" ")
                    BinOp(op, Var(left), Var(right))
                }
        }
    }

    @Test
    fun test1() {
        val data = Aoc.getInput(21)
            .lineSequence()
            .map {
                val (name, expr) = it.split(":")
                name to Expr.parse(expr.trim())
            }
            .toMap()

        val result = data["root"]!!.eval(data)

        println(result)
    }

    @Test
    fun test2() {
        val example = """
            |root: pppw + sjmn
            |dbpl: 5
            |cczh: sllz + lgvd
            |zczc: 2
            |ptdq: humn - dvpt
            |dvpt: 3
            |lfqf: 4
            |humn: 5
            |ljgn: 2
            |sjmn: drzm * dbpl
            |sllz: 4
            |pppw: cczh / lfqf
            |lgvd: ljgn * ptdq
            |drzm: hmdt - zczc
            |hmdt: 32
        """.trimMargin()

        val data = Aoc.getInput(21)
            .lineSequence()
            .map {
                val (name, expr) = it.split(":")
                name to Expr.parse(expr.trim())
            }
            .toMap()

        val result = data["root"]!!.eval(data)

        println(result)
    }

}