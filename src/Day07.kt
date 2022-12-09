import java.util.*

sealed class Node {
    abstract val name: String
    abstract val size: Int

    data class FileNode(override val name: String, override val size: Int) : Node()

    data class DirNode(override val name: String, val nodes: MutableSet<Node> = mutableSetOf()) : Node() {
        override val size: Int by lazy {
            nodes.sumOf(Node::size)
        }
    }
}

fun main() {
    fun processData(data: List<String>): Node.DirNode {
        val dirTree = Node.DirNode("/")
        val nodeStackTrace = Stack<Node.DirNode>()

        for (execution in data.joinToString("\n")
            .split("$")
            .filter(String::isNotEmpty)
            .map { it.split("\n") }) {
            val command = execution.first()

            // parse command
            when (command.substring(1..2)) {
                "cd" -> {
                    when (val name = command.substring(4)) {
                        "/" -> nodeStackTrace += dirTree
                        ".." -> nodeStackTrace.pop()
                        else -> nodeStackTrace.peek()
                            .nodes
                            .filterIsInstance<Node.DirNode>()
                            .find { it.name == name }
                            .let(nodeStackTrace::push)
                    }
                }

                "ls" -> {
                    for (file in execution.subList(1, execution.size - 1)) {
                        val (size, name) = file.split(" ")

                        if (file.startsWith("dir")) {
                            nodeStackTrace.peek().nodes += Node.DirNode(name)
                        } else {
                            nodeStackTrace.peek().nodes += Node.FileNode(name, size.toInt())
                        }
                    }
                }
            }
        }

        return dirTree
    }

    fun findDirs(dirNode: Node.DirNode): List<Node.DirNode> =
        dirNode.nodes
            .filterIsInstance<Node.DirNode>()
            .let { if (it.isNotEmpty()) it + it.flatMap(::findDirs) else emptyList() }


    fun part1(data: Node.DirNode): Int =
        findDirs(data).map(Node::size)
            .sorted()
            .filter { it < 100_000 }
            .sum()

    fun part2(data: Node.DirNode): Int =
        findDirs(data).map(Node::size)
            .sorted()
            .first { it >= (30_000_000 - (70_000_000 - data.size)) }

    val input = readInput("Day07")
    val processedData = processData(input)

    println(part1(processedData))
    println(part2(processedData))
}
