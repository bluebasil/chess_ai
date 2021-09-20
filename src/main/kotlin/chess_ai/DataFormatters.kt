package chess_ai

import org.codehaus.jettison.json.JSONObject
import org.yaml.snakeyaml.Yaml
import java.io.File

class YamlObj constructor(yaml: LinkedHashMap<String,Any?>?) {
    val map: LinkedHashMap<String,Any?>? = yaml

    fun getObject(key: String): YamlObj {
        return YamlObj(map?.get(key) as LinkedHashMap<String,Any?>?)
    }

    fun getNullableString(key: String): String? {
        return map?.get(key) as String?
    }

    fun getString(key: String): String {
        return map?.get(key) as String
    }

    fun getStringList(key: String): List<String> {
        return map?.get(key) as List<String>
    }

    fun set(key: String, value: String?) {
        map?.put(key, value)
    }

    fun set(key: String, value: YamlObj) {
        map?.put(key, value.map)
    }

    fun getKeys(): Set<String> {
        return map?.keys as Set<String>
    }

    fun isNull(): Boolean {
        return map == null
    }

    fun dump(): String {
        return Yaml().dump(map)
    }

    companion object {
        fun load(file: File): YamlObj {
            return YamlObj(Yaml().load<LinkedHashMap<String,Any?>>(file.readText(Charsets.UTF_8)))
        }

        fun load(yamlString: String): YamlObj {
            return YamlObj(Yaml().load<LinkedHashMap<String,Any?>>(yamlString))
        }
    }
}


fun loadJson(filePath: String): JSONObject {
    return JSONObject(File(filePath).readText(Charsets.UTF_8))
}