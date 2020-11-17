package chess_ai
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler

class Handler : RequestHandler<Map<String, String>, String>{
    override fun handleRequest(event: Map<String, String>?, context: Context?): String {
        println("Hello World!")
        println(context)
        println(event)
        test(arrayOf())
        return "Success"
    }
}
