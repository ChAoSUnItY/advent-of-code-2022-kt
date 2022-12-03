fun main() {
    fun Char.priority(): Int =
        if (isLowerCase()) code - 97 + 1
        else code - 65 + 27

    fun part1(data: List<String>): Int =
        data.map { it.chunked(it.length / 2, CharSequence::toSet) }
            .sumOf {
                it.reduce { acc, compartment -> acc.intersect(compartment) }
                    .first()
                    .priority()
            }

    fun part2(data: List<String>): Int =
        data.chunked(3) { it.map(String::toSet) }
            .sumOf {
                it.reduce { acc, element -> acc.intersect(element) }
                    .first()
                    .priority()
            }


    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}