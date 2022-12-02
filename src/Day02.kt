fun main() {
    val map = mapOf(
        'A' to mapOf(
            'X' to (3 + 1 to 3 + 0),
            'Y' to (6 + 2 to 1 + 3),
            'Z' to (0 + 3 to 2 + 6)
        ),
        'B' to mapOf(
            'X' to (0 + 1 to 1 + 0),
            'Y' to (3 + 2 to 2 + 3),
            'Z' to (6 + 3 to 3 + 6)
        ),
        'C' to mapOf(
            'X' to (6 + 1 to 2 + 0),
            'Y' to (0 + 2 to 3 + 3),
            'Z' to (3 + 3 to 1 + 6)
        )
    )

    fun part1(data: List<Pair<Char, Char>>): Int =
        data.sumOf { map[it.first]!![it.second]!!.first }

    fun part2(data: List<Pair<Char, Char>>): Int =
        data.sumOf { map[it.first]!![it.second]!!.second }

    fun processData(data: List<String>): List<Pair<Char, Char>> =
        data.map { it[0] to it[2] }

    val input = readInput("Day02")
    val processedInput = processData(input)
    println(part1(processedInput))
    println(part2(processedInput))
}