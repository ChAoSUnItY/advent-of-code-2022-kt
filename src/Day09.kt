import kotlin.math.abs
import kotlin.math.sign

fun main() {
    data class Position(val x: Int = 0, val y: Int = 0) {
        fun offset(x: Int = 0, y: Int = 0): Position =
            copy(x = this.x + x, y = this.y + y)
    }

    data class Instruction(val direction: Char, val steps: Int)

    fun processData(data: List<String>): List<Instruction> =
        data.map {
            val (direction, step) = it.split(" ")
            Instruction(direction[0], step.toInt())
        }

    fun moveHead(head: Position, direction: Char): Position = when (direction) {
        'U' -> head.offset(y = 1)
        'D' -> head.offset(y = -1)
        'R' -> head.offset(x = 1)
        'L' -> head.offset(x = -1)
        else -> head
    }

    fun followHead(head: Position, tail: Position): Position = when {
        head.x == tail.x && abs(head.y - tail.y) > 1 -> tail.offset(y = (head.y - tail.y).sign)
        head.y == tail.y && abs(head.x - tail.x) > 1 -> tail.offset(x = (head.x - tail.x).sign)
        abs(head.y - tail.y) + abs(head.x - tail.x) >= 3 -> tail.offset(
            x = (head.x - tail.x).sign,
            y = (head.y - tail.y).sign
        )

        else -> tail
    }

    fun traverse(data: List<Instruction>, length: Int): Int {
        val traversedPositions = mutableSetOf<Position>()
        val rope = Array(length) { Position() }

        for ((direction, step) in data) {
            for (_i in 0 until step) {
                rope[0] = moveHead(rope[0], direction)

                for (i in 1 until rope.size) 
                    rope[i] = followHead(rope[i - 1], rope[i])

                traversedPositions += rope.last()
            }
        }

        return traversedPositions.size
    }

    fun part1(data: List<Instruction>): Int =
        traverse(data, 2)

    fun part2(data: List<Instruction>): Int =
        traverse(data, 10)

    val input = readInput("Day09")
    val data = processData(input)

    println(part1(data))
    println(part2(data))
}
