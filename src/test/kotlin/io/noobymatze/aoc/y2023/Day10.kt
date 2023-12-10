package io.noobymatze.aoc.y2023

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day10 {

    data class Pos(val row: Int, val col: Int) {
        val right get() = Pos(row, col + 1)
        val left get() = Pos(row, col - 1)
        val top get() = Pos(row - 1, col)
        val bottom get() = Pos(row + 1, col)
    }

    @Test
    fun test() {
        val input = Aoc.getInput(10)
            .lines()
        val row = input.indexOfFirst { 'S' in it }
        val col = input[row].indexOf('S')
        val s = Pos(row, col)

        operator fun List<String>.get(pos: Pos): Char? =
            getOrNull(pos.row)?.getOrNull(pos.col)

        fun neighbors(pos: Pos): Set<Pos> {
            return when (input[pos]) {
                '7' -> setOfNotNull(pos.bottom, pos.left)
                'L' -> setOfNotNull(pos.top, pos.right)
                'J' -> setOfNotNull(pos.top, pos.left)
                'F' -> setOfNotNull(pos.bottom, pos.right)
                '-' -> setOfNotNull(pos.left, pos.right)
                '|' -> setOfNotNull(pos.top, pos.bottom)
                'S' -> setOfNotNull(
                    pos.top.takeIf { input[it] in setOf('|', 'F', '7') },
                    pos.bottom.takeIf { input[it] in setOf('|', 'L', 'J') },
                    pos.right.takeIf { input[it] in setOf('-', '7', 'J') },
                    pos.left.takeIf { input[it] in setOf('-', 'F', 'L') },
                )
                else -> emptySet()
            }.filter { (row, col) -> 0 <= row && row < input.size && 0 <= col && col < input[row].length }
                .toSet()
        }

        val nodes = mutableListOf(s)
        val distances = mutableMapOf(s to 0)
        val known = mutableSetOf<Pos>()
        while (nodes.isNotEmpty()) {
            val node = nodes.removeFirst()
            known.add(node)
            val next = neighbors(node).filter { it !in known }
            nodes.addAll(next)
            next.forEach { distances[it] = distances[node]!! + 1 }
        }

        print(input, known)

        println(distances.maxOf { it.value })
    }

    @Test
    fun test2() {
        // I am absolutely not proud of this, but I don't know how to do it better...yet.

        val test = """
            FF7FSF7F7F7F7F7F---7
            L|LJ||||||||||||F--J
            FL-7LJLJ||||||LJL-77
            F--JF--7||LJLJ7F7FJ-
            L---JF-JLJ.||-FJLJJ7
            |F|F-JF---7F7-L7L|7|
            |FFJF7L7F-JF7|JL---7
            7-L-JL7||F7|L7F-7F7|
            L.L7LFJ|||||FJL7||LJ
            L7JLJL-JLJLJL--JLJ.L
        """.trimIndent()
        val input = Aoc.getInput(10)
            .lines()
        val row = input.indexOfFirst { 'S' in it }
        val col = input[row].indexOf('S')
        val s = Pos(row, col)

        var cur = setOfNotNull(
            s.top.takeIf { input[it] in setOf('|', 'F', '7') },
            s.bottom.takeIf { input[it] in setOf('|', 'L', 'J') },
            s.right.takeIf { input[it] in setOf('-', '7', 'J') },
            s.left.takeIf { input[it] in setOf('-', 'F', 'L') },
        ).first()

        var dir = when {
            cur.row < s.row -> 'n'
            cur.row > s.row -> 's'
            cur.col > s.col -> 'e'
            cur.col < s.col -> 'w'
            else -> throw IllegalStateException("Poof")
        }

        val movement = mapOf(
            ('n' to '|') to 'n',
            ('n' to 'F') to 'e',
            ('n' to '7') to 'w',
            ('s' to '|') to 's',
            ('s' to 'L') to 'e',
            ('s' to 'J') to 'w',
            ('e' to 'J') to 'n',
            ('e' to '7') to 's',
            ('e' to '-') to 'e',
            ('w' to '-') to 'w',
            ('w' to 'F') to 's',
            ('w' to 'L') to 'n',
        )

        val segments = mutableSetOf(s)
        do {
            segments.add(cur)

            val v = input[cur]
            dir = movement[dir to v]!!
            when (dir) {
                'n' -> cur = cur.top
                'e' -> cur = cur.right
                'w' -> cur = cur.left
                's' -> cur = cur.bottom
            }
        } while (cur != s)

        val pipe = segments.toSet()

        val newInput = input.mapIndexed { row, line ->
            line.mapIndexed { col, c -> if (Pos(row, col) in pipe) c else '.' }
                .joinToString("")
        }

        //println(newInput)

        cur = setOfNotNull(
            s.top.takeIf { input[it] in setOf('|', 'F', '7') },
            s.bottom.takeIf { input[it] in setOf('|', 'L', 'J') },
            s.right.takeIf { input[it] in setOf('-', '7', 'J') },
            s.left.takeIf { input[it] in setOf('-', 'F', 'L') },
        ).first()

        dir = when {
            cur.row < s.row -> 'n'
            cur.row > s.row -> 's'
            cur.col > s.col -> 'e'
            cur.col < s.col -> 'w'
            else -> throw IllegalStateException("Poof")
        }

        val lefts = mutableSetOf<Pos>()
        do {
            lefts.addAll(newInput.rights(cur, dir))
            val v = input[cur]
            dir = movement[dir to v]!!
            when (dir) {
                'n' -> cur = cur.top
                'e' -> cur = cur.right
                'w' -> cur = cur.left
                's' -> cur = cur.bottom
            }
        } while (cur != s)

        println(lefts.size)

        print(input, pipe, lefts)
    }

    private fun List<String>.rights(start: Pos, dir: Char): Set<Pos> {
        val dots = mutableSetOf<Pos>()
        var cur = rightMove(start, dir)
        while (this[cur] == '.') {
            dots.add(cur)
            cur = rightMove(cur, dir)
        }

        when {
            this[start] == '7' && dir == 'n' -> {
                var cur = rightMove(start, 'w')
                while (this[cur] == '.') {
                    dots.add(cur)
                    cur = rightMove(cur, 'w')
                }
            }
            this[start] == '7' && dir == 'n' -> {
                var cur = rightMove(start, 'w')
                while (this[cur] == '.') {
                    dots.add(cur)
                    cur = rightMove(cur, 'w')
                }
            }

        }
        return dots
    }

    private fun rightMove(pos: Pos, dir: Char): Pos = when (dir) {
        'n' -> pos.right
        'e' -> pos.bottom
        's' -> pos.left
        'w' -> pos.top
        else -> throw IllegalStateException("Poof $dir")
    }

    private fun print(input: List<String>, known: Set<Pos>, hashes: Set<Pos> = emptySet()) {
        println(input.mapIndexed { row, line ->
            line.mapIndexed { col, c -> if (Pos(row, col) in known) c else if (Pos(row, col) in hashes) '#' else '.' }
                .map {
                    when (it) {
                        '7' -> '┐'
                        'L' -> '└'
                        'F' -> '┌'
                        'J' -> '┘'
                        '|' -> '│'
                        '-' -> '─'
                        else -> it
                    }
                }
                .joinToString("")
        }.joinToString("\n"))
    }

    operator fun List<String>.get(pos: Pos): Char? =
        getOrNull(pos.row)?.getOrNull(pos.col)
}
