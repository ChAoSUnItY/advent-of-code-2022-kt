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

    // Optimized, per element is iterated 4 times from different directions,
    // which means the time complexity is O((N ^ 2) * 4), where N is size of data (N ^ 2)
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

    fun part1Unoptimized(data: List<List<Int>>): Int =
        data.flatMapIndexed { y, row ->
            row.mapIndexed { x, height ->
                gridCrossWalk(data.size, x, y).mapIndexed { i, gridWalk ->
                    gridWalk.map {
                        if (i < 2) data[y][it] else data[it][x]
                    }.all { it < height }
                }.any { it }
            }.filter { it }
        }.count()

    fun part2(data: List<List<Int>>): Int =
        data.flatMapIndexed { y, row ->
            row.mapIndexed { x, treeHouseHeight ->
                gridCrossWalk(data.size, x, y).mapIndexed { i, gridWalk ->
                    gridWalk.map {
                        if (i < 2) data[y][it] else data[it][x]
                    }.takeWhileInclusive { // Collections.kt
                        it < treeHouseHeight
                    }.count()
                }.reduce(Int::times)
            }
        }.max()

    val data = readInput("Day08")
    val processedData = processData(data)
    println(part1(processedData))
    // println(part1Unoptimized(processedData))
    println(part2(processedData))
}
