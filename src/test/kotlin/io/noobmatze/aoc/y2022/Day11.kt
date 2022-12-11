package io.noobmatze.aoc.y2022

import kotlin.test.Test

class Day11 {

    data class Monkey(
        val items: MutableList<Long>,
        val op: (Long) -> Long,
        val test: Long,
        val then: Int,
        val `else`: Int,
        var inspections: Long = 0,
    )

    @Test
    fun test() {
        val monkeys = Aoc.getInput(11)
            .split("\n\n").map { parse(it) }
        repeat(20) {
            monkeys.forEach { monkey ->
                while (monkey.items.isNotEmpty()) {
                    val next = monkey.items.removeFirst()
                    val worryLevel = monkey.op(next)
                    monkey.inspections += 1
                    val result = worryLevel / 3
                    if (result % monkey.test == 0L) {
                        monkeys[monkey.then].items.add(result)
                    }
                    else {
                        monkeys[monkey.`else`].items.add(result)
                    }
                }
            }
        }

        val (a, b) = monkeys.sortedByDescending { it.inspections }.take(2)
        println(a.inspections * b.inspections)
    }

    @Test
    fun test2() {
        val testInput = """
            |Monkey 0:
            |  Starting items: 79, 98
            |  Operation: new = old * 19
            |  Test: divisible by 23
            |    If true: throw to monkey 2
            |    If false: throw to monkey 3
            |
            |Monkey 1:
            |  Starting items: 54, 65, 75, 74
            |  Operation: new = old + 6
            |  Test: divisible by 19
            |    If true: throw to monkey 2
            |    If false: throw to monkey 0
            |
            |Monkey 2:
            |  Starting items: 79, 60, 97
            |  Operation: new = old * old
            |  Test: divisible by 13
            |    If true: throw to monkey 1
            |    If false: throw to monkey 3
            |
            |Monkey 3:
            |  Starting items: 74
            |  Operation: new = old + 3
            |  Test: divisible by 17
            |    If true: throw to monkey 0
            |    If false: throw to monkey 1""".trimMargin()
        val monkeys = testInput // Aoc.getInput(11)
            .split("\n\n").map { parse(it) }
        val initials = monkeys.map { it.items.toList() }
        repeat(10000) {
            monkeys.forEachIndexed { index, monkey ->
                var i = 0
                while (monkey.items.isNotEmpty()) {
                    val next = monkey.items.removeFirst()
                    var result = monkey.op(next)
                    monkey.inspections += 1

                    result %= initials[index][i % initials[index].size]
                    if (result % monkey.test == 0L) {
                        monkeys[monkey.then].items.add(result)
                    }
                    else {
                        monkeys[monkey.`else`].items.add(result)
                    }
                    i++
                }
            }
        }

        val (a, b) = monkeys.sortedByDescending { it.inspections }.take(2)
        println(a.inspections * b.inspections)
    }

    fun parse(input: String): Monkey {
        val numberRegex = Regex("\\d+")
        val lines = input.lines()
        val items = numberRegex.findAll(lines[1]).map { it.value.toLong() }.toMutableList()
        val operation = lines[2].split(": ")[1].let { parseOperation(it.split(" ")) }
        val test = Regex("\\d+").find(lines[3])!!.value.toLong()
        val then = Regex("\\d+").find(lines[4])!!.value.toInt()
        val `else` = Regex("\\d+").find(lines[5])!!.value.toInt()
        return Monkey(items, operation, test, then, `else`)
    }

    fun parseOperation(
        op: List<String>
    ): (Long) -> Long = { old ->
        val a = if (op[2] == "old") old else op[2].toLong()
        val b = if (op[4] == "old") old else op[4].toLong()
        when {
            op[3] == "*" -> a * b
            op[3] == "/" -> a / b
            op[3] == "-" -> a - b
            else -> a + b
        }
    }

}