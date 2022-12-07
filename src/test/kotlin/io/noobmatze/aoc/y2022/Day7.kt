package io.noobmatze.aoc.y2022

import kotlin.test.Test

class Day7 {

    sealed interface Tree {
        data class Folder(val name: String, val children: List<Tree>): Tree
        data class File(val name: String, val size: Int): Tree
    }

    @Test
    fun test() {
        val wd = mutableListOf<String>()
        val map = mutableMapOf<String, Int>()
        val result = Aoc.getInput(7).lines()
        var pos = 0
        fun isAtEnd(): Boolean = pos >= result.size
        fun peek(): String = result[pos]
        while (!isAtEnd()) {
            val next = result[pos++].trim().split(" ")
            when {
                next[0] == "$" && next[1] == "cd" -> when (next[2]) {
                    ".." -> wd.removeLast()
                    else -> {
                        map.putIfAbsent(wd.joinToString("/") + "/" + next[2], 0)
                        wd.add(next[2])
                    }
                }

                next[0] == "$" && next[1] == "ls" -> {
                    while (!isAtEnd() && !peek().startsWith("$")) {
                        val value = result[pos++]
                        if (!value.startsWith("dir")) {
                            val tmp = mutableListOf(*wd.toTypedArray())
                            while (tmp.isNotEmpty()) {
                                map.compute(tmp.joinToString("/")) { _, count ->
                                    (count ?: 0) + value.split(" ")[0].toInt()
                                }
                                tmp.removeLast()
                            }
                        }
                    }
                }

                else ->
                    throw IllegalArgumentException()
            }
        }

        val value = map.filter { it.value <= 100000 }.map { it.value }.sum()
        println(value)
    }

    @Test
    fun test2() {
        val wd = mutableListOf<String>()
        val map = mutableMapOf<String, Int>()
        val result = Aoc.getInput(7).lines()
        var pos = 0
        fun isAtEnd(): Boolean = pos >= result.size
        fun peek(): String = result[pos]
        while (!isAtEnd()) {
            val next = result[pos++].trim().split(" ")
            when {
                next[0] == "$" && next[1] == "cd" -> when (next[2]) {
                    ".." -> wd.removeLast()
                    else -> {
                        map.putIfAbsent(wd.joinToString("/") + "/" + next[2], 0)
                        wd.add(next[2])
                    }
                }

                next[0] == "$" && next[1] == "ls" -> {
                    while (!isAtEnd() && !peek().startsWith("$")) {
                        val value = result[pos++]
                        if (!value.startsWith("dir")) {
                            val tmp = mutableListOf(*wd.toTypedArray())
                            while (tmp.isNotEmpty()) {
                                map.compute(tmp.joinToString("/")) { _, count ->
                                    (count ?: 0) + value.split(" ")[0].toInt()
                                }
                                tmp.removeLast()
                            }
                        }
                    }
                }

                else ->
                    throw IllegalArgumentException()
            }
        }

        println(70000000 - map["/"]!!)
        val value = map.filter { it.value >= (30000000 - (70000000 - map["/"]!!)) }.minBy { it.value }
        println(value)
    }

}