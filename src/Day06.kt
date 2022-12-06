fun main() {
    fun part1(data: String): Int {
        for (count in 4 until data.length) {
            if (data.substring(count - 4 until count)
                    .toSet()
                    .size == 4
            ) {
                return count
            }
        }

        return 0
    }

    fun part2(data: String): Int {
        for (count in 14 until data.length) {
            if (data.substring(count - 14 until count)
                    .toSet()
                    .size == 14
            ) {
                return count
            }
        }

        return 0
    }

    val input = readInput("Day06")[0]
    
    println(part1(input))
    println(part2(input))
}
