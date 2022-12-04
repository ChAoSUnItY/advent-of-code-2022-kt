fun main() {
    fun part1(data: List<List<IntRange>>): Int =
        data.sumOf {
            val (l, r) = it.take(2)
            val intersectedSize = l.intersect(r).size

            if (intersectedSize == l.count() || intersectedSize == r.count()) 1.toInt() else 0
        }

    fun part2(data: List<List<IntRange>>): Int =
        data.sumOf {
            val (l, r) = it.take(2)

            if (l.intersect(r).isNotEmpty()) 1.toInt() else 0
        }

    fun processData(data: List<String>): List<List<IntRange>> =
        data.map {
            it.split(',')
                .map { range ->
                    range.split('-')
                        .map { n -> n.toInt() }
                }
                .map { range ->
                    range[0]..range[1]
                }
        }

    val input = readInput("Day04")
    val processedInput = processData(input)
    println(part1(processedInput))
    println(part2(processedInput))
}