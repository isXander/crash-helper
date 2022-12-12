package dev.isxander.crashhelper.analysis.method.impl

import com.google.gson.JsonObject
import dev.isxander.crashhelper.analysis.method.Method
import dev.isxander.crashhelper.analysis.method.MethodDecoder

class And(private val conditions: List<Method>) : Method {
    override fun check(crash: String): Boolean {
        return conditions.all { it.check(crash) }
    }

    object Decoder : MethodDecoder {
        override fun decode(json: JsonObject): Method {
            return And(MethodDecoder.getConditions(json))
        }
    }
}