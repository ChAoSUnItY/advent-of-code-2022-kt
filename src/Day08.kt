typealias GridWalk = Pair<IntProgression, IntProgression>

fun main() {
    fun getRanges(size: Int): List<IntProgression> =
        listOf((0 until size), (size - 1 downTo 0))

    fun directions(size: Int): List<GridWalk> =
        getRanges(size).flatMap { y -> getRanges(size).map { x -> y to x } }

    fun part1(data: List<String>): Int {
        val heightMap = data.map {
            it.toCharArray()
                .filter(Char::isDigit)
                .map(Char::digitToInt)
        }
        val size = data.size
        val visible = Array(size) { BooleanArray(size) }

        for ((i, gridWalk) in directions(size).withIndex()) {
            val (xWalk, yWalk) = gridWalk

            for (y in yWalk) {
                var currentMaxHeight = -1

                for (x in xWalk) {
                    val currentHeight =
                        if (i % 2 == 0) heightMap[x][y]
                        else heightMap[y][x]

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

    val data = readInput("Day08")
    println(part1(data))
}
