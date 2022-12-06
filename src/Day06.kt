fun main() {
    fun part1(data: String): Int =
        data.windowed(4)
            .indexOfFirst { it.toSet().size == it.length } + 4

    fun part2(data: String): Int =
        data.windowed(14)
            .indexOfFirst { it.toSet().size == it.length } + 14

    val input = readInput("Day06")[0]
    
    println(part1(input))
    println(part2(input))
}
