fun main() {
    fun part1(input: List<String>): Int {
        var most = 0
        val calories = mutableListOf<Int>()

        for ((i, line) in input.withIndex()) {
            if (line.isBlank() || i == input.lastIndex && calories.isNotEmpty()) {
                val sum = calories.sum()
                if (sum > most) {
                    most = sum
                }
                calories.clear()
            } else {
                calories += line.toInt()
            }
        }

        return most
    }

    fun part2(input: List<String>): Int {
        val all = mutableListOf<Int>()
        val calories = mutableListOf<Int>()

        for ((i, line) in input.withIndex()) {
            if (line.isBlank() || i == input.lastIndex && calories.isNotEmpty()) {
                all += calories.sum()
                calories.clear()
            } else {
                calories += line.toInt()
            }
        }

        var sum = 0

        for (i in 0 until 3) {
            val currentMax = all.max()

            all.remove(currentMax)

            sum += currentMax
        }

        return sum
    }

//    val input = readInput("Day01_Example")
//    println(part1(input))
//    println(part2(input))

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
