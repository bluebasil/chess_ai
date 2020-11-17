package chess_ai

import com.amazonaws.services.lambda.runtime.Context


fun main(args: Array<String>) {
    val h = Handler()
    val m = mapOf("a" to "b", "c" to "d")
    h.handleRequest(m,null)
}

fun test(args: Array<String>) {
    println("Hello, World!")
    val c = Cell()
    println(c.empty)
}

