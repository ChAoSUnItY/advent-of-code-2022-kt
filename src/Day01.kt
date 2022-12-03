fun main() {
    fun part1(input: List<String>): Int =
        input.joinToString(separator = "\n")
            .split("\n\n").maxOf {
                it.split("\n")
                    .map(String::toInt)
                    .sum()
            }

    fun part2(input: List<String>): Int =
        input.joinToString(separator = "\n")
            .split("\n\n").map {
                it.split("\n")
                    .map(String::toInt)
                    .sum()
            }
            .sortedDescending()
            .take(3)
            .sum()

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
