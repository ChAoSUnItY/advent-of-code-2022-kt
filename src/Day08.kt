import kotlin.math.max

typealias GridWalk = Pair<IntProgression, IntProgression>
typealias GridCrossWalk = IntProgression

fun main() {
    fun getRanges(size: Int): List<IntProgression> =
        listOf((0 until size), (size - 1 downTo 0))

    fun getRanges(size: Int, start: Int): List<IntProgression> =
        listOf((start - 1 downTo 0), (start + 1 until size))

    fun gridWalk(size: Int): List<GridWalk> =
        getRanges(size).flatMap { y -> getRanges(size).map { x -> y to x } }

    fun gridCrossWalk(size: Int, startX: Int, startY: Int): List<GridCrossWalk> =
        listOf(startX, startY).flatMap { getRanges(size, it) }
    
    fun processData(data: List<String>): List<List<Int>> =
        data.map {
            it.toCharArray()
                .filter(Char::isDigit)
                .map(Char::digitToInt)
        }

    fun part1(data: List<List<Int>>): Int {
        val size = data.size
        val visible = Array(size) { BooleanArray(size) }

        for ((i, gridWalk) in gridWalk(size).withIndex()) {
            val (xWalk, yWalk) = gridWalk

            for (y in yWalk) {
                var currentMaxHeight = -1

                for (x in xWalk) {
                    val currentHeight =
                        if (i % 2 == 0) data[x][y]
                        else data[y][x]

                    if (currentHeight > currentMaxHeight) {
                        currentMaxHeight = currentHeight

                        if (x == 0 || y == 0 || x == size - 1 || y == size - 1)
                            continue

                        if (i % 2 == 0) visible[x][y] = true
                        else visible[y][x] = true
                    }
                }
            }
        }

        return visible.flatMap(BooleanArray::toList)
            .count { it } + size * 4 - 4
    }

    fun part2(data: List<List<Int>>): Int {
        val size = data.size
        var maxScenicScore = 0

        for ((y, row) in data.withIndex()) {
            for ((x, treeHouseHeight) in row.withIndex()) {
                val directionScenicScores = IntArray(4)

                for ((i, gridWalk) in gridCrossWalk(size, x, y).withIndex()) {
                    for (offset in gridWalk) {
                        directionScenicScores[i]++
                        val height =
                            if (i < 2) data[y][offset]
                            else data[offset][x]
                        if (height >= treeHouseHeight) break
                    }
                }

                maxScenicScore = max(directionScenicScores.fold(1, Int::times), maxScenicScore)
            }
        }

        return maxScenicScore
    }

    val data = readInput("Day08")
    val processedData = processData(data)
    println(part1(processedData))
    println(part2(processedData))
}
