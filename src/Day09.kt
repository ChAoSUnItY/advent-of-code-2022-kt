import kotlin.math.abs
import kotlin.math.sign

fun main() {
    data class Position(val x: Int = 0, val y: Int = 0)
    data class Instruction(val direction: Char, val steps: Int)
    
    fun processData(data: List<String>): List<Instruction> =
        data.map {
            val (direction, step) = it.split(" ")
            Instruction(direction[0], step.toInt())
        }

    fun followHead(head: Position, tail: Position): Position = when {
        head.x == tail.x && abs(head.y - tail.y) > 1 -> Position(tail.x, tail.y + (head.y - tail.y).sign)
        head.y == tail.y && abs(head.x - tail.x) > 1 -> Position(tail.x + (head.x - tail.x).sign, tail.y)
        abs(head.y - tail.y) + abs(head.x - tail.x) >= 3 -> Position(
            tail.x + (head.x - tail.x).sign,
            tail.y + (head.y - tail.y).sign
        )
        else -> tail
    }

    fun traverse(data: List<Instruction>, children: Int): Int {
        val traversedPositions = mutableSetOf<Position>()
        var head = Position()
        val rope = Array(children) { Position() }

        for ((direction, step) in data) {
            for (_i in 0 until step) {
                when (direction) {
                    'U' -> head = Position(head.x, head.y + 1)
                    'D' -> head = Position(head.x, head.y - 1)
                    'R' -> head = Position(head.x + 1, head.y)
                    'L' -> head = Position(head.x - 1, head.y)
                }

                for (i in rope.indices) {
                    val first = if (i == 0) head else rope[i - 1]
                    val tail = rope[i]
                    val newPosition = followHead(first, tail)
                    rope[i] = newPosition
                }

                traversedPositions += rope.last()
            }
        }

        return traversedPositions.size
    }

    fun part1(data: List<Instruction>): Int =
        traverse(data, 1)

    fun part2(data: List<Instruction>): Int =
        traverse(data, 9)

    val input = readInput("Day09")
    val data = processData(input)

    println(part1(data))
    println(part2(data))
}
