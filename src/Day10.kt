fun main() {
    data class CycleState(
        val crtGeneration: Boolean,
        var cycle: Int = 0,
        var regX: Int = 1,
        var rayPosition: Int = 0,
        var signalSum: Int = 0,
        var crtImage: MutableList<Char> = mutableListOf()
    ) {
        fun processInstruction(instruction: String): CycleState {
            val instructionSegments = instruction.split(" ")

            when (instructionSegments[0]) {
                "noop" -> {
                    nextCycle(1)
                }

                "addx" -> {
                    nextCycle(2)
                    regX += instructionSegments[1].toInt()
                }

                else -> {}
            }

            return this
        }

        fun nextCycle(cycleCount: Int) {
            for (_i in 0 until cycleCount) {
                crtImage +=
                    if (rayPosition >= regX - 1 && rayPosition <= regX + 1) '.'
                    else '#'

                if (crtGeneration && ++rayPosition % 40 == 0) {
                    rayPosition = 0
                    crtImage += '\n'
                } else if (++cycle == 20 || (cycle - 20) % 40 == 0) {
                    signalSum += cycle * regX
                }
            }
        }
    }

    fun part1(data: List<String>): Int =
        data.fold(CycleState(false), CycleState::processInstruction).signalSum

    fun part2(data: List<String>): String =
        data.fold(CycleState(true), CycleState::processInstruction).crtImage.joinToString("")

    val input = readInput("Day10")

    println(part1(input))
    println(part2(input))
}
