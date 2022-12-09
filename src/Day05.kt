import java.util.*

fun main() {
    val instructionRegex = Regex("\\d+")

    data class Instruction(val count: Int, val from: Int, val to: Int)

    fun parseStackData(data: List<String>): List<LinkedList<Char>> {
        val stackSize = (data[0].length + 1) / 4
        val stacks = MutableList(stackSize) { LinkedList<Char>() }

        for (line in data.dropLast(1)) {
            val layerElements = line.chunked(4)

            for ((i, element) in layerElements.withIndex()) {
                if (element.isBlank()) continue

                stacks[i].add(element[1])
            }
        }

        return stacks
    }

    fun processInstructions(data: List<String>): List<Instruction> =
        data.map {
            val (count, from, to) = instructionRegex.findAll(it)
                .map { match -> match.value.toInt() }
                .toList()

            Instruction(count, from, to)
        }

    fun processData(data: List<String>): Pair<List<LinkedList<Char>>, List<Instruction>> {
        val (stackGraph, instructions) = data.joinToString("\n")
            .split("\n\n")
            .map { it.split("\n") }
        return parseStackData(stackGraph) to processInstructions(instructions)
    }

    fun part1(data: Pair<List<LinkedList<Char>>, List<Instruction>>): String {
        val (stacks, instructions) = data

        for ((count, from, to) in instructions) {
            (0 until count).map { stacks[from - 1].pop() }
                .forEach { stacks[to - 1].push(it) }
        }

        return stacks.map(LinkedList<Char>::peek)
            .joinToString("")
    }

    fun part2(data: Pair<List<LinkedList<Char>>, List<Instruction>>): String {
        val (stacks, instructions) = data

        for ((count, from, to) in instructions) {
            (0 until count).map { stacks[from - 1].pop() }
                .reversed()
                .forEach { stacks[to - 1].push(it) }
        }

        return stacks.map(LinkedList<Char>::peek)
            .joinToString("")
    }

    val input = readInput("Day05")

    println(part1(processData(input)))
    println(part2(processData(input)))
}
