package dev.isxander.crashhelper.analysis.method.impl

import com.google.gson.JsonObject
import dev.isxander.crashhelper.analysis.method.Method
import dev.isxander.crashhelper.analysis.method.MethodDecoder
import dev.isxander.crashhelper.analysis.method.impl.Regex

class Regex(private val regex: kotlin.text.Regex) : Method {
    override fun check(crash: String): Boolean {
        return regex.containsMatchIn(crash)
    }

    object Decoder : MethodDecoder {
        override fun decode(json: JsonObject): Method {
            return Regex(json["regex"].asString.toRegex())
        }
    }
}