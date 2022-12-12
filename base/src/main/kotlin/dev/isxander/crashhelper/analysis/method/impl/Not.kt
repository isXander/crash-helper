package dev.isxander.crashhelper.analysis.method.impl

import com.google.gson.JsonObject
import dev.isxander.crashhelper.analysis.method.Method
import dev.isxander.crashhelper.analysis.method.MethodDecoder

class Not(private val method: Method) : Method {
    override fun check(crash: String): Boolean {
        return !method.check(crash)
    }

    object Decoder : MethodDecoder {
        override fun decode(json: JsonObject): Method {
            return Not(MethodDecoder.decode(json["check"].asJsonObject))
        }
    }
}