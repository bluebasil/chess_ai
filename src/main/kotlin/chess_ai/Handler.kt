package chess_ai
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import java.util.*

class Handler : RequestHandler<Any, String>{
    override fun handleRequest(event: Any, context: Context?): String {
        println("#${event::class.simpleName}")
        println("#${event::class.qualifiedName}")
        println("Starting...")
        val input = parseInput(event)
        println(input)
        //println(context)

        val inputData = YamlObj.load(input)
        //println(inputData)

//        val outTile: YamlObj? = followMovePath(inputData.getObject("tiles"),"bc")
//        if (outTile == null) {
//            println("invalid path")
//        }
//        else {
//            println(tileContentsString(outTile))
//        }

        val move:YamlObj = inputData.getObject("move")
        //println("should be true:")
        val destination:YamlObj? = validateMove(move, "white")
        //println(destination)
        if (destination != null) {
            executeMove(move, destination)
        }

        println("---")
        return inputData.dump()
    }

    fun tileContentsString(tile: YamlObj): String {
        val has: YamlObj = tile.getObject("has")
        if (has.isNull()) {
            return "Empty"
        }
        return "${has.getString("color")} ${has.getObject("type").getString("name")}"
    }

    fun followMovePath(sourceTile: YamlObj, move: String): YamlObj? {
        var currentTile: YamlObj = sourceTile
        for (c in move) {
            //println("***")
            //println(c)
            //println(currentTile.isNull())
            if (currentTile.isNull()) {
                return null
            }
            currentTile = currentTile.getObject(c.toString())
        }
        if (currentTile.isNull()) {
            return null
        }
        return currentTile
    }

    // given the move obj
    fun validateMove(moveBlock: YamlObj?, turn: String): YamlObj?{
        if (moveBlock != null) {
            val tile = moveBlock.getObject("tile")
            val pieceInstance = tile.getObject("has")
            if (pieceInstance.getString("color") != turn) {
                println("Rejected move: wrong turn")
                return null
            }

            // Check capture set
            // Then check first turn moveset if on first turn
            // then check moveset

            val move:String = moveBlock.getString("move")
            if (!pieceInstance.getObject("type").getStringList("moveset").contains(move)) {
                println("Rejected move: move not in moveset")
                return null
            }

            val destination: YamlObj? = followMovePath(tile,move)
            if (destination == null) {
                println("Rejected move: off of board")
                return null
            }
            return destination
        }
        println("Rejected move: no move found")
        return null
    }

    // given the move obj - assumes that move has already been validated
    fun executeMove(moveBlock: YamlObj, destination: YamlObj) {
        val sourceTile = moveBlock.getObject("tile")
        val pieceInstance = sourceTile.getObject("has")
        sourceTile.set("has",null)
        destination.set("has",pieceInstance)
    }


    fun parseInput(inputMap: Any): String {
        if (inputMap is HashMap<*, *>) {
            if (inputMap.containsKey("body") and inputMap.containsKey("isBase64Encoded")) {
                val rawInput = inputMap["body"]
                if (rawInput is String) {
                    val input: String
                    if ((inputMap["isBase64Encoded"] == "true")) {
                        input = String(Base64.getDecoder().decode(rawInput))
                    } else {
                        input = rawInput
                    }

                    return input
                }
            }
        }
        throw IllegalArgumentException("Error parsing input")
    }
}
