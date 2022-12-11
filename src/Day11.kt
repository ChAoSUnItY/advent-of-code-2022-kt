import java.util.*

fun main() {
    val perMonkeyPattern =
        Regex("""Monkey \d*:\n {2}Starting items: ([\w, ]+)\n {2}Operation: (new = [ (old)+*\d]+)\n {2}Test: divisible by (\d+)\n {4}If true: throw to monkey (\d+)\n {4}If false: throw to monkey (\d+)""")
    val operationPattern =
        Regex("""new = old ([*|+]) (\d+|old)""")

    data class Monkey(
        val items: LinkedList<Long>,
        val operation: (Long) -> Long,
        val divisibleValue: Long,
        val trueBranch: Int,
        val falseBranch: Int,
        var inspectedItems: Long = 0
    )

    fun processOperation(data: String): (Long) -> Long {
        val (operator, rhs) = operationPattern.matchEntire(data)!!.destructured

        return when (operator) {
            "*" -> {
                when (rhs) {
                    "old" -> { a -> a * a }
                    else -> { a -> a * rhs.toLong() }
                }
            }

            "+" -> {
                when (rhs) {
                    "old" -> { a -> a + a }
                    else -> { a -> a + rhs.toLong() }
                }
            }

            else -> { a -> a }
        }
    }

    fun processData(data: List<String>): List<Monkey> =
        data.joinToString("\n")
            .split("\n\n")
            .map {
                val (items, operation, divisibleValue, trueThrowTo, falseThrowTo) = perMonkeyPattern.matchEntire(it)!!.destructured
                val processedItems = items.split(",")
                    .map(String::trim)
                    .mapTo(LinkedList(), String::toLong)

                Monkey(
                    processedItems,
                    processOperation(operation),
                    divisibleValue.toLong(),
                    trueThrowTo.toInt(),
                    falseThrowTo.toInt()
                )
            }

    fun startRound(monkeys: List<Monkey>, worryUpdater: (Long) -> Long) {
        for (monkey in monkeys) {
            while (monkey.items.isNotEmpty()) {
                monkey.inspectedItems++

                val item = monkey.items
                    .pollFirst()
                    .let(monkey.operation::invoke)
                    .let(worryUpdater)
                val branch =
                    if (item % monkey.divisibleValue == 0L) monkey.trueBranch
                    else monkey.falseBranch

                monkeys[branch].items += item
            }
        }
    }

    fun runRounds(round: Int, monkeys: List<Monkey>, worryUpdater: (Long) -> Long): Long {
        for (_i in 0 until round) {
            startRound(monkeys, worryUpdater)
        }

        return monkeys.map(Monkey::inspectedItems)
            .sortedDescending()
            .take(2)
            .reduce(Long::times)
    }

    fun part1(monkeys: List<Monkey>): Long =
        runRounds(20, monkeys) { it / 3 }

    fun part2(monkeys: List<Monkey>): Long =
        runRounds(10_000, monkeys) { it % monkeys.map(Monkey::divisibleValue).fold(1, Long::times) }

    val input = readInput("Day11")

    println(part1(processData(input)))
    println(part2(processData(input)))
}
