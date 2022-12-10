package io.noobmatze.aoc.y2022

import kotlin.test.Test

class Day10 {

    @Test
    fun test() {
        val data = Aoc.getInput(10)
            .lineSequence()
            .map { it.split(" ") }
            .map { if (it.size > 1) it[0] to it[1].toInt() else it[0] to null }

        var signal = 0
        var x = 1
        var cycle = 0
        for ((instr, amount) in data) {
            when (instr) {
                "noop" -> {
                    cycle++
                    if ((cycle - 20) % 40 == 0) {
                        signal += cycle * x
                    }
                }
                "addx" -> {
                    for (i in (1.. 2)) {
                        if (((cycle + i) - 20) % 40 == 0) {
                            signal += (cycle + i) * x
                        }
                    }

                    x += amount!!
                    cycle += 2
                }
            }
        }

        println(signal)
    }

    @Test
    fun test2() {
        val testInput = """
            |addx 15
            |addx -11
            |addx 6
            |addx -3
            |addx 5
            |addx -1
            |addx -8
            |addx 13
            |addx 4
            |noop
            |addx -1
            |addx 5
            |addx -1
            |addx 5
            |addx -1
            |addx 5
            |addx -1
            |addx 5
            |addx -1
            |addx -35
            |addx 1
            |addx 24
            |addx -19
            |addx 1
            |addx 16
            |addx -11
            |noop
            |noop
            |addx 21
            |addx -15
            |noop
            |noop
            |addx -3
            |addx 9
            |addx 1
            |addx -3
            |addx 8
            |addx 1
            |addx 5
            |noop
            |noop
            |noop
            |noop
            |noop
            |addx -36
            |noop
            |addx 1
            |addx 7
            |noop
            |noop
            |noop
            |addx 2
            |addx 6
            |noop
            |noop
            |noop
            |noop
            |noop
            |addx 1
            |noop
            |noop
            |addx 7
            |addx 1
            |noop
            |addx -13
            |addx 13
            |addx 7
            |noop
            |addx 1
            |addx -33
            |noop
            |noop
            |noop
            |addx 2
            |noop
            |noop
            |noop
            |addx 8
            |noop
            |addx -1
            |addx 2
            |addx 1
            |noop
            |addx 17
            |addx -9
            |addx 1
            |addx 1
            |addx -3
            |addx 11
            |noop
            |noop
            |addx 1
            |noop
            |addx 1
            |noop
            |noop
            |addx -13
            |addx -19
            |addx 1
            |addx 3
            |addx 26
            |addx -30
            |addx 12
            |addx -1
            |addx 3
            |addx 1
            |noop
            |noop
            |noop
            |addx -9
            |addx 18
            |addx 1
            |addx 2
            |noop
            |noop
            |addx 9
            |noop
            |noop
            |noop
            |addx -1
            |addx 2
            |addx -37
            |addx 1
            |addx 3
            |noop
            |addx 15
            |addx -21
            |addx 22
            |addx -6
            |addx 1
            |noop
            |addx 2
            |addx 1
            |noop
            |addx -10
            |noop
            |noop
            |addx 20
            |addx 1
            |addx 2
            |addx 2
            |addx -6
            |addx -11
            |noop
            |noop
            |noop
        """.trimMargin()

        val data = Aoc.getInput(10)
            .lineSequence()
            .map { it.split(" ") }
            .map { if (it.size > 1) it[0] to it[1].toInt() else it[0] to null }
            .toList()

        val screen = Array(240) {  "." }
        var x = 1
        var cycle = 0
        for ((instr, amount) in data) {
            when (instr) {
                "noop" -> {
                    val pixel = cycle % screen.size
                    val valueInRow = pixel.rem(40)
                    if (valueInRow == x || valueInRow == x - 1 || valueInRow == x + 1)
                        screen[pixel] = "#"
                    else
                        screen[pixel] = "."
                    cycle++
                }
                "addx" -> {
                    for (i in (1.. 2)) {
                        val pixel = (cycle + i) % screen.size
                        val valueInRow = pixel.rem(40)
                        if (valueInRow == x || valueInRow == x - 1 || valueInRow == x + 1)
                            screen[pixel] = "#"
                        else
                            screen[pixel] = "."
                    }

                    x += amount!!
                    // Yes, I know, I know, this was a hack to see if I can
                    // get by without fully understanding, what I am doing.
                    // Suffice to say, unfortunately it worked. ;-)
                    val pixel = (cycle + 2) % screen.size
                    val valueInRow = pixel.rem(40)
                    if (valueInRow == x || valueInRow == x - 1 || valueInRow == x + 1)
                        screen[pixel] = "#"
                    else
                        screen[pixel] = "."
                    cycle += 2
                }
            }
        }

        screen.print()
    }

    private fun Array<String>.print() {
        for (i in 0 until 6) {
            val line = (0 until 40).joinToString("") { k ->
                this[(i * 40) + k]
            }
            println(line)
        }
    }

}