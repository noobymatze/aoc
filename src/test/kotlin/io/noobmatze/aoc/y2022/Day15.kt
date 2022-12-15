package io.noobmatze.aoc.y2022

import io.noobmatze.aoc.y2022.Aoc.findAllByGroup
import kotlin.math.abs
import kotlin.math.max
import kotlin.test.Test

class Day15 {

    data class Pos(val row: Int, val col: Int) {

        infix fun distance(other: Pos): Int =
            abs(row - other.row) + abs(col - other.col)
    }

    data class Sensor(val pos: Pos, val beacon: Pos) {

        val distance: Int = pos distance beacon

        val edge: List<Pos> get() = buildList {
            add(Pos(pos.row - distance - 1, pos.col))
            add(Pos(pos.row + distance + 1, pos.col))
            for (row in (pos.row-distance..pos.row+distance)) {
                val index = abs(row - pos.row)
                val cols = distance - index
                add(Pos(row, pos.col - cols - 1))
                add(Pos(row, pos.col + cols + 1))
            }
        }

        operator fun contains(other: Pos): Boolean =
            pos distance other <= distance

    }

    @Test
    fun test() {
        val example = """
            |Sensor at x=2, y=18: closest beacon is at x=-2, y=15
            |Sensor at x=9, y=16: closest beacon is at x=10, y=16
            |Sensor at x=13, y=2: closest beacon is at x=15, y=3
            |Sensor at x=12, y=14: closest beacon is at x=10, y=16
            |Sensor at x=10, y=20: closest beacon is at x=10, y=16
            |Sensor at x=14, y=17: closest beacon is at x=10, y=16
            |Sensor at x=8, y=7: closest beacon is at x=2, y=10
            |Sensor at x=2, y=0: closest beacon is at x=2, y=10
            |Sensor at x=0, y=11: closest beacon is at x=2, y=10
            |Sensor at x=20, y=14: closest beacon is at x=25, y=17
            |Sensor at x=17, y=20: closest beacon is at x=21, y=22
            |Sensor at x=16, y=7: closest beacon is at x=15, y=3
            |Sensor at x=14, y=3: closest beacon is at x=15, y=3
            |Sensor at x=20, y=1: closest beacon is at x=15, y=3
        """.trimMargin()
        val regex = Regex("=(?<value>-?\\d+)")
        val searched = 2000000
        val data = Aoc.getInput(15)
            .lineSequence()
            .map { line ->
                val (cS, rS, cB, rB) = regex.findAllByGroup(line, "value", String::toInt).toList()
                Pos(rS, cS) to Pos(rB, cB)
            }
            .flatMap { (sensor, beacon) ->
                val distance = sensor distance beacon
                val bottom = sensor.row + distance
                val top = sensor.row - distance
                if (searched in top..bottom) {
                    val cols = distance - abs(searched - sensor.row)
                    if (beacon.row == searched)
                        (sensor.col-cols..sensor.col+cols) - beacon.col
                    else
                        (sensor.col-cols..sensor.col+cols)
                }
                else {
                    emptyList()
                }
            }
            .toSet().size

        println(data)
    }

    @Test
    fun test2() {
        val example = """
            |Sensor at x=2, y=18: closest beacon is at x=-2, y=15
            |Sensor at x=9, y=16: closest beacon is at x=10, y=16
            |Sensor at x=13, y=2: closest beacon is at x=15, y=3
            |Sensor at x=12, y=14: closest beacon is at x=10, y=16
            |Sensor at x=10, y=20: closest beacon is at x=10, y=16
            |Sensor at x=14, y=17: closest beacon is at x=10, y=16
            |Sensor at x=8, y=7: closest beacon is at x=2, y=10
            |Sensor at x=2, y=0: closest beacon is at x=2, y=10
            |Sensor at x=0, y=11: closest beacon is at x=2, y=10
            |Sensor at x=20, y=14: closest beacon is at x=25, y=17
            |Sensor at x=17, y=20: closest beacon is at x=21, y=22
            |Sensor at x=16, y=7: closest beacon is at x=15, y=3
            |Sensor at x=14, y=3: closest beacon is at x=15, y=3
            |Sensor at x=20, y=1: closest beacon is at x=15, y=3
        """.trimMargin()
        val regex = Regex("=(?<value>-?\\d+)")
        val sensors = Aoc.getInput(15)
            .lineSequence()
            .map { line ->
                val (cS, rS, cB, rB) = regex.findAllByGroup(line, "value", String::toInt).toList()
                val sensor = Pos(rS, cS)
                val beacon = Pos(rB, cB)
                Sensor(sensor, beacon)
            }.toList()

        val maxCol = sensors.maxOf { it.pos.col }
        val minCol = sensors.minOf { it.pos.col }
        val colBounds = minCol..maxCol
        val rowBounds = 0..4000000

        for (sensor in sensors) {
            val otherSensors = sensors.filter { it.pos != sensor.pos }
            val result = sensor.edge.filter { it.row in rowBounds && it.col in colBounds }
                .find { edgePos -> otherSensors.none { edgePos in it } }
            if (result != null) {
                println(result)
                println(result.col.toLong() * 4000000L + result.row.toLong())
                break
            }
        }
    }

}