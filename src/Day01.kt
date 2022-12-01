fun main() {
    fun part1(input: List<String>): Int =
        input.joinToString(separator = "\n")
            .split("\n\n").maxOfOrNull {
                it.split("\n")
                    .map(String::toInt)
                    .sum()
            } ?: 0

    fun part2(input: List<String>): Int =
        input.joinToString(separator = "\n")
            .split("\n\n").map {
                it.split("\n")
                    .map(String::toInt)
                    .sum()
            }
            .sortedDescending()
            .subList(0, 3)
            .sum()

//    val input = readInput("Day01_Example")
//    println(part1(input))
//    println(part2(input))

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
