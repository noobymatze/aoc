package io.noobymatze.aoc.y2023

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day5 {

    data class Mapping(
        val dest: Long,
        val src: Long,
        val range: Long,
    )

    @Test
    fun test() {
        val input = Aoc.getInput(5)
            .split("\n\n")

        val seeds = input[0].drop("seeds: ".length).split(" ").map { it.toLong() }
        val maps = input.drop(1).map { map ->
            map.lines().drop(1).map { line ->
                val (dest, src, range) = line.split(" ").map { it.toLong() }
                Mapping(dest, src, range)
            }
        }

        val result = seeds.map { seed ->
            maps.fold(seed) { value, mappings ->
                mappings
                    .find { it.src <= value && value < it.src + it.range  }
                    ?.let { map -> map.dest + (value - map.src) }
                    ?: value
            }
        }.min()

        println(result)
    }

    @Test
    fun test2() {
        val input = Aoc.getInput(5)
            .split("\n\n")

        val seeds = input[0].drop("seeds: ".length).split(" ").map { it.toLong() }
            .windowed(2, 2)

        val maps = input.drop(1).map { map ->
            map.lines().drop(1).map { line ->
                val (dest, src, range) = line.split(" ").map { it.toLong() }
                Mapping(dest, src, range)
            }
        }

        val result = seeds.asSequence()
            .flatMap { (start, range) -> start until (start + range) }
            .map { seed ->
                maps.fold(seed) { value, mappings ->
                    mappings
                        .find { it.src <= value && value < it.src + it.range  }
                        ?.let { map -> map.dest + (value - map.src) }
                        ?: value
                }
            }.min()

        println(result)
    }

}