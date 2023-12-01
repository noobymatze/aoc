package io.noobymatze.aoc.y2022

import io.noobymatze.aoc.Aoc
import kotlin.test.Test

class Day8 {

    data class Pos(val row: Int, val col: Int)

    @Test
    fun test() {
        val data = Aoc.getInput(8).lines()
            .flatMapIndexed { row, line -> line.mapIndexed { col, c -> (Pos(row, col)) to c.digitToInt() }}
            .toMap()

        val maxRow = data.keys.maxBy { it.row }.row
        val maxCol = data.keys.maxBy { it.col }.col
        val visible = data.filter { (pos, value) ->
            val left = (0 until pos.col).all { data[pos.copy(col = it)]!! < value }
            val right = (pos.col + 1 .. maxCol).all { data[pos.copy(col = it)]!! < value }
            val bottom = (pos.row + 1.. maxRow).all { data[pos.copy(row = it)]!! < value }
            val top = (0 until pos.row).all { data[pos.copy(row = it)]!! < value }
            left || right || bottom || top
        }

        println(visible.size)
    }

    @Test
    fun test2() {
        val data = Aoc.getInput(8).lines()
            .flatMapIndexed { row, line -> line.mapIndexed { col, c -> (Pos(row, col)) to c.digitToInt() }}
            .toMap()

        val maxRow = data.keys.maxBy { it.row }.row
        val maxCol = data.keys.maxBy { it.col }.col
        val visible = data.filterNot { it.key.row == 0 || it.key.col == 0 || it.key.row == maxRow || it.key.col == maxRow }.map { (pos, value) ->
            val left = (pos.col - 1 downTo 0).find { data[pos.copy(col = it)]!! >= value }?.let {
                pos.col - it
            } ?: pos.col

            val right = (pos.col + 1 .. maxCol).find { data[pos.copy(col = it)]!! >= value }?.let {
                it - pos.col
            } ?: (maxCol - pos.col)

            val bottom = (pos.row + 1.. maxRow).find { data[pos.copy(row = it)]!! >= value }?.let {
                it - pos.row
            } ?: (maxRow - pos.row)

            val top = (pos.row - 1 downTo 0).find { data[pos.copy(row = it)]!! >= value }?.let {
                pos.row - it
            } ?: pos.row

            left * right * bottom * top
        }.maxBy { it }

        println(visible)
    }

}