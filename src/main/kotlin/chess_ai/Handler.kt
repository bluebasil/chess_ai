package chess_ai
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.beust.klaxon.JsonObject

class Handler : RequestHandler<Any, String>{
    override fun handleRequest(event: Any, context: Context?): String {
        println("Hello World!")
        println(context)

        test(arrayOf())

        if (event is String) {
            println("#String")
            println(event)
        } else if (event is JsonObject) {
            println("#json")
            println(event)
        } else {
            println("#${event::class.simpleName}")
            println("#${event::class.qualifiedName}")
            println(event)
        }

        return "Success"
    }
}
