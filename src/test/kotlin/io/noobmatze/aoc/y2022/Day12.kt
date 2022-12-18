package io.noobmatze.aoc.y2022

import kotlinx.coroutines.*
import java.util.PriorityQueue
import kotlin.test.Test

class Day12 {

    data class Vertex(val row: Int, val col: Int)
    fun neighbors(v: Vertex): List<Vertex> =
        listOf(
            v.copy(row = v.row + 1),
            v.copy(row = v.row - 1),
            v.copy(col = v.col - 1),
            v.copy(col = v.col + 1),
        )

    fun Char.height(): Int = when (this) {
        'E' -> 'z'.height()
        'S' -> 'a'.height()
        else -> code - 97
    }

    @Test
    fun test() {
        val example = """
            |Sabqponm
            |abcryxxl
            |accszExk
            |acctuvwj
            |abdefghi
        """.trimMargin()

        val data = Aoc.getInput(12)
            .lineSequence()
            .flatMapIndexed { row, line ->
                line.mapIndexed { col, c -> Vertex(row, col) to c }
            }
            .toMap()

        val start = data.firstNotNullOf { (key, value) -> key.takeIf { value == 'S' } }
        val end = data.firstNotNullOf { (key, value) -> key.takeIf { value == 'E' } }
        val prev = mutableMapOf<Vertex, Vertex>()
        val dist = data.map { it.key to Int.MAX_VALUE }.toMap().toMutableMap()
        dist[start] = 0
        val queue = PriorityQueue<Vertex> { a, b -> dist[a]!!.compareTo(dist[b]!!) }
        data.forEach { queue.add(it.key) }
        while (queue.isNotEmpty()) {
            val u = queue.minBy { dist[it]!! }
            queue.remove(u)
            if (u == end) break

            val neighbors = neighbors(u).filter {
                it in queue && data[it]!!.height() <= data[u]!!.height() + 1
            }

            for (v in neighbors) {
                val old = dist[u]!! + 1
                if (old < dist[v]!!) {
                    dist[v] = old
                    prev[v] = u
                }
            }
        }

        val path = mutableListOf<Vertex>()
        var target: Vertex? = end
        while (target != null) {
            path.add(0, target)
            target = prev[target]
        }

        println(path.size)
    }

    @Test
    fun test2() {
        val example = """
            |Sabqponm
            |abcryxxl
            |accszExk
            |acctuvwj
            |abdefghi
        """.trimMargin()

        val input = Aoc.getInput(12)
        val data = input
            .lineSequence()
            .flatMapIndexed { row, line ->
                line.mapIndexed { col, c -> Vertex(row, col) to c }
            }
            .toMap()

        val starts = data.filter { (_, value) -> value == 'a' || value == 'S' }.keys
        val end = data.firstNotNullOf { (key, value) -> key.takeIf { value == 'E' } }

        val results = runBlocking {
            starts.mapIndexed { i, start ->
                async(Dispatchers.Default) {
                    val prev = mutableMapOf<Vertex, Vertex>()
                    val dist = data.map { it.key to Int.MAX_VALUE }.toMap().toMutableMap()
                    dist[start] = 0
                    val queue = mutableListOf<Vertex>()
                    data.forEach { queue.add(it.key) }
                    while (queue.isNotEmpty()) {
                        val u = queue.minBy { dist[it]!! }
                        queue.remove(u)
                        if (u == end) break

                        val neighbors = neighbors(u).filter {
                            it in queue && data[it]!!.height() <= data[u]!!.height() + 1
                        }

                        for (v in neighbors) {
                            val old = dist[u]!! + 1
                            if (old < dist[v]!!) {
                                dist[v] = old
                                prev[v] = u
                            }
                        }
                    }

                    val path = mutableListOf<Vertex>()
                    var target: Vertex? = end
                    while (target != null) {
                        path.add(0, target)
                        target = prev[target]
                    }

                    println("$i/${starts.size}, ${start.row}, ${start.col}: ${path.size}")

                    path
                }
            }.awaitAll()
        }

        val path = results.minBy { it.size }.toSet()

        Aoc.getInput(12).lines().forEachIndexed { row, line ->
            println(line.asSequence().withIndex().joinToString("") {
                if (Vertex(row, it.index) in path) "." else it.value.toString()
            })
        }

        println(results.minOf { it.size } - 1)
    }

}