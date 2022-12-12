package dev.isxander.crashhelper.analysis.method.impl

import com.google.gson.JsonObject
import dev.isxander.crashhelper.analysis.method.Method
import dev.isxander.crashhelper.analysis.method.MethodDecoder

class Contains(private val query: String, private val ignoreCase: Boolean) : Method {
    override fun check(crash: String): Boolean {
        return crash.contains(query, ignoreCase)
    }

    object Decoder : MethodDecoder {
        override fun decode(json: JsonObject): Method {
            return Contains(json["query"].asString, json.has("ignore_case") && json["ignore_case"].asBoolean)
        }
    }
}