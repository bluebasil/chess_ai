package chess_ai
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler

class Handler : RequestHandler<Map<String, String>, String>{
    override fun handleRequest(event: Map<String, String>?, context: Context?): String {
        val logger = context!!.logger
        println("Hello World!")
        logger.log("CONTEXT: $context")
        println(event)
        main(arrayOf())
        return "Success"
    }
}
