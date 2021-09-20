package chess_ai

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.codehaus.jettison.json.JSONObject
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.util.*
import kotlin.collections.LinkedHashMap


fun main(args: Array<String>) {

    val dataFile:YamlObj = YamlObj.load(File("src/main/resources/minimalSet.yaml"))


    println(triggerHandler(dataFile.dump()))
}



fun triggerHandler(input: String): String {
    // load input as jsonObject
    var json = loadJson("src/main/resources/apiInput.json")
    // inject desired input
    json.put("body", input)
    // converts to map
    val result: HashMap<*, *>? = ObjectMapper().readValue(json.toString(), HashMap::class.java)
    val h = Handler()
    // send result to handler
    if (result != null) {

//        var test: LinkedHashMap<Any, Any> = LinkedHashMap()
//        test.put("isBase64Encoded","false")
//        test.put("body","ahhhh")

        return h.handleRequest(result, null)
    }
    return "ERROR"
}

